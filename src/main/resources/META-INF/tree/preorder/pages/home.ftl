<#import "html.ftl" as html>
<!DOCTYPE html>
<html>
<@html.head title="数据库中的前序树">
<link rel="stylesheet" href="${pluginPath}/static/jstree/themes/default/style.min.css"/>
<script type="text/javascript" src="${pluginPath}/static/jstree/jstree.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('#tree').on('move_node.jstree', function (event, data) {
            console.log(data)
            var tree = $('#tree').jstree(true);
            var parent = tree.get_node(data.parent);
            console.log(parent)
            $.post("${pluginPath}/move/" +
                   data.node.id,
                    {targetParentId: parent.id, afterBrotherId: parent.children[data.position - 1]},
                    function () {
                        tree.refresh();
                    })
        }).jstree({
            'core': {
                'data': {
                    'url': function (node) {
                        console.log(node)
                        return "${pluginPath}/load";
                    },
                    'data': function (node) {
                        return { 'id': node.id };
                    }
                },
                'check_callback': true,
                'expand_selected_onload': true
            },
            'plugins': [ 'wholerow', 'checkbox', 'dnd', 'search' ],
            'checkbox': {
                'three_state': false
            }
        });

        $("#create").click(function () {
            var tree = $('#tree').jstree(true);
            var nodes = tree.get_selected(true);

            if (!nodes.length) {
                return false;
            }

            var selected = nodes[0];
            console.log(selected)

            $.post("${pluginPath}/create/" + selected.id, function () {
                tree.refresh();
            })
        })

        $("#remove").click(function () {
            var tree = $('#tree').jstree(true);
            var nodes = tree.get_selected(true);

            if (!nodes.length) {
                return false;
            }

            var selected = nodes[0];
            console.log(selected)

            $.post("${pluginPath}/remove/" + selected.id, function () {
                tree.refresh();
            })
        })

        $("#init").click(function () {
            $.get("${pluginPath}/init", function () {
                self.location.reload();
            })
        })
    });

</script>
</@html.head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="row">
                <div class="col-md-4 col-sm-8 col-xs-12">
                    <button id="create" type="button" class="btn btn-success btn-sm">
                        <i class="glyphicon glyphicon-asterisk"></i> Create
                    </button>
                    <button id="remove" type="button" class="btn btn-danger btn-sm">
                        <i class="glyphicon glyphicon-remove"></i> Delete
                    </button>
                    <button id="init" type="button" class="btn btn-info btn-sm">
                        <i class="glyphicon glyphicon-gift"></i> Init
                    </button>
                </div>

                <a href="https://github.com/pippo1980/rdbms-tree">
                    <h4><span class="label label-info">https://github.com/pippo1980/rdbms-tree</span></h4>
                </a>
            <#--<div class="col-md-2 col-sm-4 col-xs-4" style="text-align:right;">-->
            <#--<input type="text" value=""-->
            <#--style="box-shadow:inset 0 0 4px #eee; width:120px; margin:0; padding:6px 12px; border-radius:4px; border:1px solid silver; font-size:1.1em;"-->
            <#--id="demo_q" placeholder="Search">-->
            <#--</div>-->
            </div>
        </div>
        <div class="panel-body">
            <div class="row">

                <div class="col-xs-4 col-md-5">
                    <div>
                        <h4><span class="label label-default">Demo说明</span></h4>
                        <ul>
                            <li>选择一个节点,点击新增按钮</li>
                            <li>选择一个节点,点击删除</li>
                            <li>选择一个节点,直接拖动</li>
                        </ul>
                        可以发现在节点变更之后,整颗树的左右权值被重新计算
                    </div>
                    <div id="tree"></div>
                </div>

                <div class="col-xs-8 col-md-5">
                    <h2>关系数据库中的前序遍历树</h2>

                    <h4><span class="label label-default">在数据库中存储树状结构经常会面临以下问题</span></h4>
                    <ul>
                        <li>方便的插入/更新/移动节点</li>
                        <li>树节点的关联查询(包含子节点),例如查询部门及子部门下所有的用户</li>
                    </ul>

                    <h4>
                        <span class="label label-default">比较笨的做法是为节点的每条记录上维护一列(idPath)</span>
                    </h4>
                    idPath记录当前节点从root到当前节点的所有id,用逗号分割.这样会有几个问题:
                    <ul>

                        <li>在节点维护的时候,要同时对idPath的字符串进行维护,相对不方便</li>
                        <li>在进行包含子节点的关联查询时只能使用like,性能无法保证</li>
                    </ul>

                    <h4><span class="label label-default">于是这里实现了前序遍历树在数据库中的维护,具有一下优点</span></h4>
                    <ul>
                        <li>维护简单,在插入/更新/移动节点时最多只需要条sql即可完成对整颗树的维护</li>
                        <li>在进行关联查询时可以使用每个节点上权值的范围匹配来快速的定位节点和包含的子节点</li>
                    </ul>

                    <h4><span class="label label-default">树的权值维护示意图</span></h4>
                    <a href="${pluginPath}/static/img/tree.png" class="thumbnail">
                        <img src="${pluginPath}/static/img/tree.png" alt="...">
                    </a>

                    <h4><span class="label label-default">数据库中存储的数据</span></h4>
                    <a href="${pluginPath}/static/img/tree.db.png" class="thumbnail">
                        <img src="${pluginPath}/static/img/tree.db.png" alt="...">
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>