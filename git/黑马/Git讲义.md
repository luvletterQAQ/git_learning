#1、目标

#2、概述

## 2.1、开发中的实际场景

>场景一：备份
>
>场景二：代码还原
>
>场景三：协同开发
>
>场景四：追溯问题代码的编写人和编写时间！

##2.2、版本控制器的方式

>a、集中式版本控制工具
>
>​	集中式版本控制工具，==版本库==(所有版本的代码以及提交)是集中存放在中央服务器的，team里每个人work时从中央服务器下载代码(其中一个版本)，是必须联网才能工作，局域网或互联网。个人修改后然后提交到中央版本库。
>
>b、分布式版本控制工具
>
>​	分布式版本控制系统没有“中央服务器”(可以有远程仓库方便统一管理)，每个人的电脑上都是一个完整的==版本库==(所有版本的代码都有)，这样工作的时候，无需要联网了，因为版本库就在你自己的电脑上。多人协作只需要各自的修改推送给对方(一般不这样, 一般推送到远程仓库，远程仓库和本地仓库没区别，相当于是另一个用户的仓库，所有用户都可以push)，就能互相看到对方的修改了。

## 2.3、Git

>Git是分布式的,Git不需要有中心服务器，我们每台电脑拥有的东西都是一样的。我们使用Git并且有个中心服务器，仅仅是为了方便交换大家的修改，但是这个服务器的地位和我们每个人的PC是一样的。我们可以把它当做一个开发者的pc就可以就是为了大家代码容易交流不关机用的。没有它大家一样可以工作，只不过“交换”修改不方便而已。

#3、Git安装与常用命令

## 3.1、Git环境配置

>当安装Git后首先要做的事情是设置用户名称和email地址。这是非常重要的，因为每次Git提交都会使用该用户信息。
>
>这个用户名称和邮箱仅仅是提交时表明是哪个用户提交的，为了以后追溯时使用。

### 3.1.2 基本配置

>```bash
>git config --global user.name "itcast"
>git config --global user.email "hello@itcast.cn"
>```
>
>config的三个作用域
>
>```bash
>git config --local		只对某个仓库有效
>git config --global		对当前用户的所有仓库有效
>git config --system		对系统中所有登录的用户有效
>```
>
>显式config的配置，加--list
>
>```bash
>git config --list --local	
>git config --list --global	
>git config --list --system	
>```

### 3.1.3 为常用指令配置别名（可选）

>有些常用的指令参数非常多，每次都要输入好多参数，我们可以使用别名。
>
>1. 打开用户(user)(wangyan)目录，创建 .bashrc 文件
>
>2. 在 .bashrc 文件中输入如下内容：
>
>   ```properties
>   #用于输出git提交日志
>   alias git-log='git log --pretty=oneline --all --graph --abbrev-commit'
>   #用于输出当前目录所有文件及基本信息
>   alias ll='ls -al'
>   ```
>
>3. 打开gitBash，执行 source ~/.bashrc

### 3.1.4 解决GitBash乱码问题

>1. 打开GitBash执行下面命令
>
>   ```bash
>   git config --global core.quotepath false
>   ```
>
>2. ${git_home}/etc/bash.bashrc 文件最后加入下面两行
>
>   ```properties
>   export LANG="zh_CN.UTF-8"
>   export LC_ALL="zh_CN.UTF-8"
>   ```

## 3.2、获取本地仓库

### 3.2.1、项目已存在

>把已有的项目代码纳入Git管理
>
>```bash
>cd 项目代码所在文件夹
>git init
>```

### 3.2.2、项目未创建

>直接利用git创建一个项目并交给git管理
>
>```bash
>git init 项目名          会在当前路径下创建一个文件夹，文件夹名和你的项目名一致 这个项目交给git管理
>```

### 3.2.3、.git目录

>![image-20230415122817257](C:\Users\wangyan\Desktop\2023cz\MyNote\git\黑马\assets\image-20230415122817257.png)
>
>HEAD：这个文档中的内容指向当前工作的分支(是个文件引用)，不是文件夹。
>
>![image-20230415145543631](C:\Users\wangyan\Desktop\2023cz\MyNote\git\黑马\assets\image-20230415145543631.png)
>
>refs：是个文件夹，里面有本地和远程的分支对象。
>
>![image-20230415145349374](C:\Users\wangyan\Desktop\2023cz\MyNote\git\黑马\assets\image-20230415145349374.png)
>
>![image-20230415145323338](C:\Users\wangyan\Desktop\2023cz\MyNote\git\黑马\assets\image-20230415145323338.png)![image-20230415145417310](C:\Users\wangyan\Desktop\2023cz\MyNote\git\黑马\assets\image-20230415145417310.png)
>
>Config：这个文件中写的是当前仓库的config配置，--local。
>
>![image-20230415145838785](C:\Users\wangyan\Desktop\2023cz\MyNote\git\黑马\assets\image-20230415145838785.png)
>
>Commit、tree、blob三个对象之间的关系：
>
>![image-20230415150016939](C:\Users\wangyan\Desktop\2023cz\MyNote\git\黑马\assets\image-20230415150016939.png)

## 3.3、基础操作指令

### 3.3.1、查看修改的状态（status）

> 作用：查看的修改的状态（==暂存区、工作区==）
> 命令形式：git status

### 3.3.2、添加工作区到暂存区(add)

### 3.3.3、提交暂存区到本地仓库(commit)

### 3.3.4、查看提交日志(log)

>命令形式：git log [option]
>	--all 显示所有分支
>	--pretty=oneline 将提交信息显示为一行
>	--abbrev-commit 使得输出的commitId更简短
>	--graph 以图的形式显示

### 3.3.5、版本回退

>让暂存区恢复成和HEAD一样(工作目录和HEAD不改变)：
>```bash
> git reset HEAD [-- 文件名1 文件名2...]
> git reset HEAD -- index.html 
> git reset:
>	git reset --soft <快照ID>							将HEAD指向指定快照
>	git reset --mixed <快照ID>						将HEAD指向指定快照并恢复到暂存区,mixed可省略
>	git reset --hard <快照ID>							将HEAD指向指定快照并恢复到暂存区和工作目录
>```
>
>让工作区回复成和暂存区一样：
>
>```bash
>git checkout -- <文件名>							将暂存区中的文件恢复到工作目录，暂存区不变。文件名不可省略
>```

### 3.3.6、添加文件至忽略列表

## 3.4、分支

>从分支master切换到分支temp时，会将工作目录和暂存区中属于master的文件删除，不属于master的文件保留，并且分支temp的内容复制到工作区和暂存目录。
>
>比如master分支中有a.txt文件，temp分支中有b.txt文件，此时在master分支上，在工作目录创建了个c.txt。此时工作目录中有a.txt和c.txt，暂存区中有a.txt，然后切换到temp分支，此时会将工作目录和暂存区中的a.txt删除。此时工作目录中有c.txt，而暂存区什么都没有。然后temp切换完成后，会将b.txt复制到工作目录中和暂存区中。因此c.txt不属于master和temp，这两个分支都可以对c.txt进行操作。
>
>如果master分支中有a.txt文件，temp分支中有b.txt文件，此时在master分支上，而在工作目录创建了个b.txt，此时切换分支会失败，因为temp分支中已经有了一个名叫b.txt的文件，切换到temp时要将temp分支中的b.txt复制到工作区和暂存区。会造成文件名冲突，

### 3.4.1、合并分支

>一般将其他分支如temp合并到master上，合并后temp分支不变还在原来的位置，而master分支则添加了temp的内容，将temp的内容合并到了master上。mster分支多了一次提交，其他分支不变。

### 3.4.2、解决冲突

>例子： master：a.txt, b.txt,
>			temp：a.txt, c.txt     现在要将temp合并到master上。
>
>如果两个分支中a.txt文件内容相同，则可以直接合并，合并后的master：a.txt, b.txt, c.txt。
>如果a.txt内容不同，则产生冲突，需要手动解决冲突。冲突内容在master分支(合并的目标分支)的a.txt文件中。
>
>a.txt的内容：
>
>```java
><<<<<<<HEAD    //HEAD表示当前分支
>count=1
>=======		//分界线  上面是当前分支中a.txt的内容  下面是temp分支a.txt的内容 （这里的内容是两个分支中不同的内容）
>count=2
>>>>>>>>temp	//temp分支
>count=3	//不在上面区域的内容是两个分支共同拥有的
>//用户需要修改a.txt文件的内容作为合并后的内容。然后add、commit a.txt
>```

### 3.4.3、开发中分支使用原则与流程

>在开发中，一般有如下分支使用原则与流程：
>
>master （生产） 分支
>	线上分支，主分支，中小规模项目作为线上运行的应用对应的分支；
>
>develop（开发）分支
>	是从master创建的分支，一般作为开发部门的主要开发分支，如果没有其他并行开发不同期上线要求，都可以在此版本进行开发，阶段开发完成后，需要是合并到master分支,准备上线。
>
>feature/xxxx分支
>	从develop创建的分支，一般是同期并行开发，但不同期上线时创建的分支，分支上的研发任务完成后合并到develop分支。
>
>hotfix/xxxx分支，
>	从master派生的分支，一般作为线上bug修复使用，修复完成后需要合并到master、test、develop分支。
>
>还有一些其他分支，在此不再详述，例如test分支（用于代码测试）、pre分支（预上线分支）等等。
>
>![image-20230415163949465](C:\Users\wangyan\Desktop\2023cz\MyNote\git\黑马\assets\image-20230415163949465.png)
>
>release是master上稳定的版本。里程碑。
>
>master是要上线的分支。
>
>develop是开发分支，需要上线时就合并到master分支上。
>
>feature/xxx是开发分支为完成某个业务功能而创建的分支，这个功能完成后合并到develop分支上，合并完后就可以删除。

### 3.4.4、fast-forward

>分支temp在分支master基础上多进行了一次提交，此时这两个分支在一条线上，此时temp要合并到master上。
>
>![image-20230415171205440](C:\Users\wangyan\Desktop\2023cz\MyNote\git\黑马\assets\image-20230415171205440.png)

#4、Git远程仓库

## 4.1、配置SSH公钥

> 生成SSH公钥
> 	ssh-keygen -t rsa
> 	不断回车
> 	如果公钥已经存在，则自动覆盖
> Gitee设置账户公钥
> 	获取公钥
> 	cat ~/.ssh/id_rsa.pub
> 	将公钥粘贴到码云上
> 验证是否配置成功
> 	ssh -T git@gitee.com

>远端分支发生变化后不会立即同步到本地，而是需要fetch下来，然后再进行合并。或者直接pull。

#5、在Idea中使用Git

>pdf

# 6、git指令

>```bash
>git clone:
>√	git clone <版本库的网址>
>	git clone <版本库的网址> <本地目录名>
>		clone时会自动add remote,默认的主机名为origin，可以修改为其他主机名：
>	git clone -o <远程主机名> <版本库的网址> <本地目录名>
>git remote:
>√	git remote 											列出所有远程主机名
>√	git remote -v										列出所有远程主机并列出主机网址
>	git remote show <主机名> 							可以查看主机详细信息
>√	git remote add <主机名> <网址>						添加远程主机
>	git remote rm <主机名>								删除远程主机
>	git remote rename <源主机名> <新主机名>				修改主机名
>git fetch:
>	git fetch <远程主机名>								将远程主机的更新全部取回本地
>√	git fetch <远程主机名> <远程分支名>					取回特定分支
>git pull:
>	git pull <远程主机名> <远程分支名>:<本地分支名>		取回远程某个分支并与本地分支合并
>√	git pull <远程主机名> <远程分支名>					取回远程分支并合并到当前分支
>	git pull <远程主机名>								取回与本地分支建立追踪关系的远程分支并合并到本地
>	git pull 											当前分支只有一个追踪分支
>git push:不能省略本地分支名而不省略远程分支名
>√	git push [-f] [--set-upstream] <远程主机名> <本地分支名>:<远程分支名>
>		完整写法 -f表示强制覆盖(本地分支和远程分支有冲突直接覆盖远程)
>		--set-upstream  两个分支建立追踪关系
>	git push <远程主机名> <本地分支名>:<远程分支名>		本地分支push到远程分支
>	git push <远程主机名> <本地分支名>					本地分支push到追踪的远程分支
>	git push <远程主机名> 								当前分支push到追踪的远程分支
>	git push <远程主机名> :<远程分支名>					将空的分支push到远程分支(删除远程分支)
>	git push <远程主机名> --delete <远程分支名>			删除远程分支
>√	git push 											本地分支push到追踪的远程分支
>	git push -u <远程主机名> <远程分支名>				当前分支和多个分支存在追踪关系，加上-u可以选择默认的
>
>git branch:
>	git branch 											查看本地分支
>	git branch -r										查看远程分支
>	git branch -a										查看所有分支
>	git branch -v										查看分支详细信息
>√	git branch -av
>	git branch --set-upstream-to=<远支名> <本支名>		建立追踪关系
>√	git branch -vv										可以查看追踪关系
>√	git branch <分支名>									创建新分支
>	git branch -d <分支名>									删除分支 需要做检查
>√	git branch -D <分支名>									删除分支 强制删除
>git checkout:
>√	git checkout <分支名>								切换分支
>√	git checkout -b <分支名>							创建并切换到该分支(切换到不存在的分支)
>	git checkout -- <文件名>							将暂存区中的文件恢复到工作目录，暂存区不变
>git merge:
>√	git merge <分支名>									将该分支合并到当前分支
>git diff:(-- <文件名1> <文件名2>...  可以比较指定文件)
>	git diff 											比较工作目录和暂存区所有文件
>	git diff --cached <快照ID>							比较暂存区和快照
>	git diff <快照ID>									比较工作目录和快照
>	git diff <快照ID> <快照ID> 							比较指定两个提交
>git reset：改变HEAD的指向
>	git reset --soft <快照ID>							将HEAD指向指定快照
>√	git reset --mixed <快照ID>						将HEAD指向指定快照并恢复到暂存区,mixed可省略
>√	git reset --hard <快照ID>							将HEAD指向指定快照并恢复到暂存区和工作目录
>git add:
>	git add -u：将已经被git管理了的文件全部add而不用指定文件名，比如之前多个文件已经被git管理并提交，后来这些文件被		修改了，就可以用这种方法直接全部提交。
>	git add .：将工作目录所有文件add到暂存区
>git rm:
>    git rm <文件名>											删除工作目录和暂存区指定文件
>    git rm -f <文件名>										暴力删除(如果工作目录和暂存区文件不一致)
>    git rm --cached <文件名>								只删除暂存区
>git mv:
>	git mv <源文件名> <新文件名>							修改工作目录和暂存区文件名
>git commit:
>	git commit --amend									更正最后一次提交，对最后一次提交的message进行修改
>git rebase:
>	git rebase -i <父commitID>
>git log:
>	--all 显示所有分支
>	--pretty=oneline 将提交信息显示为一行  或 --oneline
>	--abbrev-commit 使得输出的commitId更简短
>	--graph 以图的形式显示
>	-n4	只输出最近4次提交
>gitk：通过图形界面工具查看版本历史
>git cat-file：
>	git cat-file -t ace1ed38...		查看对象类型
>	git cat-file -p ace1ed38...		查看对象的内容
>```