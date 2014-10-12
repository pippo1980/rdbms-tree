<#import "../html.ftl" as html>
<!DOCTYPE html>
<html>
<@html.head title="数据库中的前序树">

</@html.head>
<body>
<div class="container">
    <div class="panel panel-default">

    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">preorder tree in rdbms</h3>
            <span>输入任意用户名密码</span>
            <span>entry any user and password</span>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col-xs-6 col-md-5">
                    <a href="#" class="thumbnail">
                        <img src="${pluginPath}/static/img/1375730753.13912201.jpg" alt="...">
                    </a>
                </div>
                <div class="col-xs-6 col-md-5">
                    <form class="form-signin" role="form" action="${pluginPath}/login/submit" method="post">
                        <h2 class="form-signin-heading">Login</h2>
                        <input name="user" type="user" class="form-control" placeholder="user" required autofocus>
                        <input name="password" type="password" class="form-control" placeholder="password" required>

                        <div>
                            <!--<label>-->
                            <!--<input type="checkbox" value="remember-me"> Remember me-->
                            <!--</label>-->
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <!--<a href="/admin/register">Register</a> &nbsp;-->
                        </div>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>