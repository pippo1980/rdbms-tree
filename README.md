rdbms-tree
==========

##关系数据库维护前序遍历树
###具有以下优点
    *维护简单,在插入/更新/移动节点时最多只需要条sql即可完成对整颗树的维护
    *在进行关联查询时可以使用每个节点上权值的范围匹配来快速的定位节点和包含的子节点
    
###[Demo](http://121.40.90.183:8082/tree/preorder/home)

###在IDEA中直接启动
    1)import to IDEA
    2)run com.sirius.plugin.framework.engine.PluginEngine.main as JAVA Application
    3)access http://127.0.0.1:8082/tree/preorder/home
    
###使用MAVEN打包启动
    1)mvn clean compile assembly:assembly
    2)cd target/rdbms-tree
    3)./shell
    4)access http://127.0.0.1:8082/tree/preorder/home
  
