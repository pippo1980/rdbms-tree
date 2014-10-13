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
                <div class="col-md-4 col-sm-8 col-xs-8">
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
            <#--<div class="col-md-2 col-sm-4 col-xs-4" style="text-align:right;">-->
            <#--<input type="text" value=""-->
            <#--style="box-shadow:inset 0 0 4px #eee; width:120px; margin:0; padding:6px 12px; border-radius:4px; border:1px solid silver; font-size:1.1em;"-->
            <#--id="demo_q" placeholder="Search">-->
            <#--</div>-->
            </div>
        </div>
        <div class="panel-body">
            <div class="row">
                <div id="tree" class="col-md-12">
                    <ul>
                        <li>Root node 1
                            <ul>
                                <li id="child_node_1">Child node 1</li>
                                <li>Child node 2</li>
                            </ul>
                        </li>
                        <li>Root node 2</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>