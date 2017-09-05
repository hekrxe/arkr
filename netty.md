# Netty 启动流程
## initAndRegister

### 一、创建 NioServerSocketChannel
1，创建一个NioServerSocketChannel对象，这里其实主要创建的对象是javaSocket（ServerSocketChannel）和pipeline对象。
2，NioServerSocketChannel对象里其实包装了一个ServerSocketChannel（javaSocket）对象
和一个pipeline(new DefaultChannelPipeline(this))对象。
3，注明该javaSocket只关心SelectionKey.OP_ACCEPT网络事件,并设置该ServerSocketChannel(javaSocket)为非阻塞。
4，创建一个DefaultServerSocketChannelConfig对象,此处主要设置RecvByteBufAllocator(该对象被用来给NioServerSocketChannel分配ByteBuf缓冲)。

以上这些过程相对于传统方式的open操作：ServerSocketChannel serverChannel = ServerSocketChannel.open();
只不过是Netty做了一些额外的封装，这些封装是值得的，对于应用来说是透明的。

### 二、初始化
1，设置ChannelOption如SO_RCVBUF（Socket缓冲区大小，默认4098。此设置最终被设置到javaSocket上）、SO_BACKLOG（套接字监听队列大小，此设置是给NioChannel的，没有设置到javaSocket上）。
2，添加一个ChannelHandler到pipeline（NioServerSocketChannel的管道）上。
	该Handler主要保存了一些将来应用到新建立连接的socket的一些参数如 childGroup, childHandler，childOptions，childAttrs 以及自己（NioServerSocketChannel）

### 三、注册
1，从group中选出一个EventLoop去执行注册流程。
2，根据当前执行线程和EventLoop的关系确定是直接开始注册还是将注册的过程封装为一个任务扔到eventLoop线程池中执行。
2，对于该流程来说，当前执行线程不是Group中的任何一个，所以此时先投递第一个任务（EventLoop也是一个ExecutorService），然后再将注册流程的任务添加进去（先简称第二任务）。
3，第一任务主要做了这几件事：
	3.1 确定是执行已经有的任务（跟注册无关的任务，可能是指用户事先指定的任务（这点没证实））还是执行监听。
	3.2 由于还没将javaSocket注册到selector上，所以是先执行已有的任务再执行注册任务。
 	注意点：该任务出发了 io.netty.channel.nio.NioEventLoop#run 方法的执行，也即是出发了 mainReactor.
4，注册任务执行(执行第二任务)
	4.1，将javaSocketChannel注册到当前线程（EventLoop）持有的selector上，并且不关心网络事件(op=0,端口还没绑定)。
	                selectionKey = javaChannel().register(eventLoop().unwrappedSelector(), 0, this/*attr*/);// 将自己作为attr参数传入注册，那么当网络事件发生时，返回的selectKey中会将该attr返回。
	4.2，通知NioServerSocketChannel上关联的pipeline触发HandlerAdded事件（此时还没开始监听套接口）。
	4.3，设置注册成功，触发 已注册事件 。
	
## doBind0
1，同样的配方，将绑定端口的过程封装为一个任务扔给线程池执行。
2，在NioServerSocketChannel中得到保存后的ServerSocketChannel,然后将它绑定到指定的端口上。
3，绑定完成后，再生成一个任务去执行，该任务主要用来通知当前的Channel已经被激活了。
	3.1，触发ChannelActive通知（此被通知到的对象是NioServerSocketChannel上的pipeline上的Handler）。
	3.2，如果配置了自动读，那么执行一次读操作。
	3.3，在读的结果中判断是否有(或配置了)我们关心的事件（对于ServerSocketChannel来说是OP_ACCEPT）,
		如果没有，那么将OP_ACCEPT事件加入到ServerSocketChannel感兴趣的网络事件集中。
4，自此服务端启动完成。