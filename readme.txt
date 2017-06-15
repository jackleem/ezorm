Git Commands:
git init 在当前目录下创建Git仓库
git add filename 将文件加入仓库
git commit -m "COMMENTS" 将变动提交到Head并附上注释
git log 查看git仓库所有提交过的历史变动， --pretty=oneline 可以更美观的输出
git status 查看当前git仓库的状态
git reset --hard VERSIONID/HEAD(^)(~N) 将Head重置为某个版本，一个^是上一个版本，^^是上两个文件，~N是上N个版本
git reflog 查看所有的历史操作命令，可以获得对应的版本号，通过reset实现重置
