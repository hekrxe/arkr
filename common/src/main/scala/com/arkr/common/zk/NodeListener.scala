package com.arkr.common.zk

import org.apache.curator.framework.recipes.cache.{NodeCache, NodeCacheListener}

/**
  * @author hztanhuayou
  * @date 2017/12/22
  */
abstract class NodeListener extends NodeCacheListener {
  private var nodeCache: NodeCache = _

  override def nodeChanged(): Unit = {
    nodeChanged(nodeCache)
  }

  def nodeChanged(node: NodeCache): Unit

  def setNodeCache(nodeCache: NodeCache): Unit = {
    this.nodeCache = nodeCache
  }

}
