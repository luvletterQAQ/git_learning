官网：https://webpack.docschina.org/concepts/

# webpack

## 1.webpack基本概念

>webpack本质是,一个第三方模块包,用于分析,并打包代码。
>它是一个模块包，识别代码，翻译,压缩,整合打包，提高打开网站的速度。
>
>![image-20230324222155393](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230324222155393.png)

## 2.webpack使用步骤

>1. 初始化包环境 得到package.json文件
>
>   ```bash
>   yarn init
>   ```
>
>2. 安装依赖包(下载webpack等模块包)
>
>   ```bash
>   yarn add webpack webpack-cli -D  //add下载的包会存放在node_moudle中
>   ```
>
>3. 配置scripts(自定义命令，为打包做准备) 加在package.json文件中
>
>   ```js
>   "scripts": {
>   	"build": "webpack"
>   }
>   ```
>
>案例需求：2个js文件->打包成1个js文件
>分析：
>
>1. 新建src下的资源
>2. add.js-定义求和函数并导出
>3. index.js-引入add模块并使用函数输出结果
>4. 执行`yarn build`自定义命令，进行打包(确保终端路径在src的父级文件夹)
>5. 打包后默认生成dist和main.js,观察其中代码
>
>![image-20230324230509031](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230324230509031.png)
>
>webpack如何使用
>
>1. 默认src/index.js -打包入口文件(必须叫这个名字index.js)
>2. 需要引入到入口的文件才会参与打包
>3. 执行package.json里build命令(自定义的)，`yarn build`执行webpack打包命令
>4. 默认输出dist/main.js的打包结果

## 3.webpack的配置

### 配置入口和出口

>1. 新建webpack.config.js
>
>   <img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230325104632092.png" alt="image-20230325104632092" style="zoom:50%;" />
>
>2. 填入配置
>
>   ```js
>   const path = require('path')
>   module.exports = {
>       entry: './src/main.js', //入口
>       output: { //出口
>           path: path.resolve(__dirname,'dist'),//出口路径文件夹的名字  __dirname是当前文件夹的绝对路径
>           filename: 'bundle.js'
>       }
>   }
>   ```
>
>3. 修改入口文件名
>
>   <img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230325104854120.png" alt="image-20230325104854120" style="zoom:50%;" />
>
>4. 打包观察效果
>
>   <img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230325104909600.png" alt="image-20230325104909600" style="zoom:50%;" />

### yarn build执行流程

<img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230325105347141.png" alt="image-20230325105347141" style="zoom:80%;" />

### yarn下的包作用在前端项目

>1. 借助webpack，把模块和代码打包
>2. 把js文件引入到html执行
>
>将下载的包导入到入口(index.js)中，然后`yarn build`到出口，将html文件手动复制到出口文件夹中，将出口中打包好的js文件引入到html代码中执行即可。

### webpack插件打包html

> webpack插件可以将指定的html文件作为模板，每次`build`时都将这个模板文件打包到出口中。
>
> https://webpack.docschina.org/plugins/html-webpack-plugin/

### webpack使用加载器打包css

>webpack默认只能处理js文件，不能处理css、html文件
>
>css-loader文档：https://webpack.docschina.org/loaders/css-loader/
>让webpack能处理css文件
>
>style-loader文档：https://webpack.docschina.org/loaders/style-loader/
>让css插入到DOM中
>
>1. 下包：
> ```bash
> yarn add css-loader style-loader -D
> ```
>
>2. webpack.config.js配置
> ```js
> module.exports = {
>   module: {//加载器配置
>     rules: [//规则  数组
>       {//一个具体的规则对象
>         test: /\.css$/i,//匹配以.css结尾的文件
>         use: ["style-loader", "css-loader"],//让webpack用这两个loader处理css文件
>           //从右到左 所以顺序不能颠倒
>           //css-loader: webpack解析css文件-把css代码一起打包进js中
>           //style-loader: css代码插入到DOM上 (style标签)
>       },
>     ],
>   },
> };
> ```
>
>3. webpack如何支持css打包?打包后样式在哪里?如何生效?
>
>  1.依赖css-loader和style-loader
>  
>  2.css代码被打包进js文件中
>  
>  3.style-loader会把css代码插入到head 下style标签内

### webpack处理less文件

### webpack处理图片

>https://webpack.docschina.org/guides/asset-modules/
>
>```js
>module.exports = {
>	module: {
>        rules: [//图片文件放到src/asset文件中
>          {//图片文件的配置(仅适用于webpack5版本)
>               test: /\.(gif|png|jpg|jpeg)$/,
>               type: 'asset',
>              //如果你设置的时asset模式
>              //以8KB大小区分图片
>              //小于8KB的 把图片转为base64  打包进js中
>              //大于  直接把文件输出到dist(出口)中
>              //如果是asset/resource 则是直接输出到dist
>              //如果是asset/inline  则是直接转为base64
>              //如果是asset/source  则是导出资源的源代码
>              //上面4种模式都是扫描asset目录下的资源
>          }
>        ]
> 	 },
>};
>```

### webpack处理字体文件

>https://webpack.docschina.org/guides/asset-modules/
>
>```js
>module.exports = {
>	module: {
>        rules: [//图片文件放到src/asset文件中
>          {//图片文件的配置(仅适用于webpack5版本)
>               test: /\.(eot|svg|ttf|woff|woff2)$/,
>               type: 'asset/resource',//所有的字体图标文件都输出到dist下
>               generator: {//生成文件名字 定义规则
>				filename: 'font/[name].[hash:6][ext]'
>			  }
>          }
>        ]
> 	 },
>};
>```

##4.webpack开发服务器

### 介绍

>问题:每次修改代码，重新yarn build打包,才能看到最新的效果,实际工作中，打包yarn build 非常费时(30s - 60s)
>
>原因:
>	从0构建依赖
>	磁盘读取对应的文件到内存, webpack开始加载
>	再用对应的 loader进行处理
>	将处理完的内容,输出到磁盘指定目录
>
>解决：起一个开发服务器,缓存一些已经打包过的内容，只重新打包修改的文件，最终运行在内存中给浏览器使用
>
>总结: webpack开发服务器,把代码运行在内存中,自动更新,实时返回给浏览器显示

### webpack-dev-server使用

>https://webpack.docschina.org/configuration/dev-server/
>
>1. 下载
>
>   ```bash
>   yarn add webpack-dev-server -D
>   ```
>
>2. 自定义webpack开发服务器启动命令serve -在package.json里
>
>   ```js
>   "scripts": {//scripts里面是自定义的命令  左边key值是自定义的
>       "build": "webpack",
>       "serve": "webpack serve"
>   },
>   ```
>
>3. 启动当前工程的webpack开发服务器
>
>   ```bash
>   yarn serve
>   ```

### 修改webpack开发服务器相关配置

>1. 去官网文档查找配置项名字
>
>2. 在webpack.config.js  - devServe选项中添加。
>
>   ```js
>   module.exports = {
>     //...
>     devServer: {
>       port: 8080,
>     },
>   };
>   ```

# @vue/cli脚手架

## 1.介绍

>@vue/cli是Vue官方提供的一个全局模块包(得到vue命令)，此包用于创建脚手架项目
>脚手架是为了保证各施工过程顺利进行而搭设的工作平台。
>
>开箱即用，0配置webpack。支持bable、css、less、开发服务器。用Vue开发项目不用自己去配置webpack。

## 2.安装

>1. ==全局安装==@vue/cli模块包
>
>   ```bash
>   yarn global add @vue/cli
>   npm install @vue/cli -g  //第二种方式
>   ```
>
>2. 查看是否成功
>
>   ```bash
>   vue -V
>   安装完后全局包会在计算机中配置全局命令(例如：vue命令)
>   ```

## 3.创建项目

>1. 创建项目
>
>   ```bash
>   vue create vuecli-demo //项目名不能有大写字母、中文、特殊符号
>   ```
>
>2. 选择模块和包管理器，等待脚手架项目创建完毕
>
>3. cd进入项目下，启动内置的webpack本地热更新开发服务器—浏览项目页面
>
>   ```bash
>   cd 项目文件
>   yarn serve
>   ```

##4.项目文件目录介绍

![image-20230325152919555](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230325152919555.png)

>main.js和App.vue,以及index.html作用和关系?
>
>1. main.js -项目打包主入口-Vue初始化
>2.  App.vue - Vue页面主入口
>3. index.html -浏览器运行的文件
>
>```js
>//main.js
>import Vue from 'vue' //引入Vue类用于下面创建Vue对象
>import App from './App.vue'//引入
>
>Vue.config.productionTip = false
>
>new Vue({//实例化Vue对象
>  render: h => h(App),//渲染App页面
>}).$mount('#app')//渲染到index.html文件里id叫app的标签上
>
>```

##5.vue/cli自定义配置

>项目中没有webpack.config.js文件，因为Vue脚手架项目用的vue.config.js
>
>src并列处新建vue.config.js,填入配置,重启webpack开发服务器
>```js
>module.exports = {
>  devServer: {
>    port: 3000,
>    open: true, //浏览器自动打开
>    lintOnSave: false //关闭eslint检查  检查语法等错误
>  }
>}

## 6.Vue文件讲解

>```vue
><!--<template>必须 只能有一个根标签-->
><template>
><!--template语块包裹的内容将会被提取、传递给 @vue/compiler-dom，
>预编译为 JavaScript 渲染函数，并附在导出的组件上作为其 render 选项。-->
>	<div>hello</div>
></template>
>
><!--js相关-->
><script>
>export default {//向外部暴露一个组件实例  上面的template标签回被编译成渲染函数 作为这个组件实例的render属性
>	name: 'App'
>}
></script>
>
><!--当前组件的样式，设置scoped，可以保证样式只对当前页面有效-->
><style scoped>
></style>
>```
>

## 7.main.js文件

> Vue el与$mount的区别 https://www.codenong.com/jse5554ea75e30/  https://www.jianshu.com/p/b8d1578d1027
>
>```js
>import Vue from 'vue'
>import App from './App.vue' //App.vue是个普通的vue文件  结构如上面6中的代码
>//其中export default 向外暴露了一个组件实例对象   在main.js中import接收并命名为App
>
>Vue.config.productionTip = false
>
>new Vue({//创建一个Vue对象
>//    el: "#app",//另一种挂载方式
>	render: h => h(App),//将App组件对象的render对应到这个vue对象上  这样就通过一个组件对象创建了一个vue对象
>}).$mount('#app')//将当前Vue对象挂载到app标签上 还可以通过vue对象的el属性来指定挂载的DOM元素(两者选其一)
>//一个 Vue 应用会将其挂载到一个 DOM 元素上 (对于这个例子是 #app) 然后对其进行完全控制
>//会覆盖掉#app标签 
>
>//vue对象的render函数和template属性的作用一样
>new Vue({
>      // render: h => h(App),
>      template: "<p>{{msg}}</p>",//template的用法 template也只能用在Runtime + Compiler 构建的 Vue 库
>      data: {
>        	msg: "666"
>  	}
>}).$mount('#app')
>//如果 render 函数和 template都不存在，挂载的目标DOM元素(#app)的 HTML 会被提取出来用作模板，此时，必须使用 Runtime + Compiler 构建的 Vue 库
>```

vue官网：https://v2.cn.vuejs.org/

## 8.Vue实例

>```js
>// 我们的数据对象
>var data = { a: 1 }
>
>// 该对象被加入到一个 Vue 实例中
>var vm = new Vue({
>  data: data
>})
>
>// 获得这个实例上的 property
>// 返回源数据中对应的字段
>vm.a == data.a // => true
>
>// 设置 property 也会影响到原始数据
>vm.a = 2
>data.a // => 2
>
>// ……反之亦然
>data.a = 3
>vm.a // => 3
>```
>
>当这些数据改变时，视图会进行重渲染。值得注意的是只有当实例被==创建时==就已经存在于 `data` 中的 property 才是**响应式**的。也就是说如果你添加一个新的 property，比如：
>```js
>vm.b = 'hi'
>```
>
>那么对 `b` 的改动将不会触发任何视图的更新。因为它是vm创建之后才添加的属性。因此如果你知道你会在晚些时候需要一个 property，但是一开始它为空或不存在，那么你仅需要设置一些初始值。
>
>Vue 实例还暴露了一些有用的实例 property 与方法。它们都有前缀 `$`，以便与用户定义的 property 区分开来。例如：
>
>```js
>var data = { a: 1 }
>var vm = new Vue({
>  el: '#example',
>  data: data
>})
>//vue类中原有的属性和方法调用时要加$  用户自定义的不用加
>//注意  vue对象中的data是属性 而组件对象中的data是方法
>//这样每个组件实例可以维护一份被返回对象的独立的拷贝，而不是共享一份数据
>//如果 Vue 没有这条规则，点击一个按钮就可能会影响到其它所有组件实例的数据
>vm.$data === data // => true
>vm.$el === document.getElementById('example') // => true
>```
>
>

# Vue指令 

## 1.插值表达式

>目标:在dom标签中,直接插入vue数据变量
>
>又叫:声明式渲染/文本插值
>
>语法：{{表达式}} 只能放表达式 不能放语句
>
>```vue
><template>
>  <div>
>    <h1>{{ msg }}</h1>
>  </div>
></template>
>
><script>
>//export default 后面跟着的是一个组件对象(可能是重写其中的方法然后让外界import)
>export default {
>  data: function () {//对象中定义函数的另一种写法 不用键值对 
>    return {//data函数返回的是一个对象，里面的对象可以在<template>中用{{}}插入
>      msg: 'hello'
>    }
>  }
>}
></script>
><style>
></style>
>```
>
>msg是vue数据变量，要在js中data函数里声明

## 2.MVVM模式

>转变思维，用数据驱动视图改变,操作dom的事vue源码内部自己做了
>
><img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230325194605260.png" alt="image-20230325194605260" style="zoom:67%;" />

## 3.v-bind

>目标：将标签的某个==属性==和某个vue==变量==绑定
>
>语法：v-bind:属性名="vue变量"    
>简写：:属性名="vue变量"
>
>```vue
><a v-bind:href="url">我是a标签</a>
><img :src="imgSrc">
><script>
>    export default {
>        data: function () {
>            return {
>                url: 'xxxxxx',
>                imgSrc: 'xxxxx'
>            }
>        }
>    }
></script>
>```

## 4.v-on

* 基本用法

>目标：给==标签==绑定==事件==
>
>语法：
>	v-on:==事件名== = "要执行的少量代码"
>	v-on:==事件名== = "methods中的函数名"
>	v-on:==事件名== = "methods中的函数名(实参)"
>	@==事件名== = "xxx"
>v-on：可以简写成@
>
>```vue
><template>
>  <div>
>    <button id="" v-on:click="addFn">+1</button>
>  </div>
></template>
>
><script>
>
>export default {
>  data: function () {  
>    return {
>      count: 1
>    }
>  },
>  methods: {//定义函数
>    addFn() { //谁调用这个方法 this就指向谁  
>              //是export default后的组件对象调用的
>      this.count++ //为什么是this.count  因为data函数
>                //会把return的对象中的属性挂到组件对象上作为组件对象的属性
>    }
>  }
>}
>```
>
>获取事件对象：
>
>```vue
><template>
>      <div>
>            <a @click="one()" href="www.baidu.com">百度</a>
>            <a @click="two(10, $event)" href="www.baidu.com">百度</a>
>      </div>
></template>
>
><script>
>
>export default {
>      data: function () {  
>            return {
>              count: 1
>            }
>      },
>      methods: {
>        	//1.事件触发  如果没有传入其他参数 则可以直接获取事件对象
>            one(e) {
>                 e.preventDefault()
>            },
>        	//2.事件触发 如果传其他参数了 需要手动传入$event
>            two(num, e) { 
>                 console.log(num)
>                 e.preventDefault()
>            }
>      }
>}
>```

* 修饰符

>语法：事件.修饰符名   给事件带来更强大的功能
>
>* 点击修饰符
>
>```vue
><a @click.prevent="one" href="www.baidu.com">阻止事件的默认行为</a>
><button @click.stop="btn">阻止事件冒泡</button>
><button @click.once="btn">程序运行期间 只触发一次事件处理函数 函数只触发一次 但事件触发了多次</button>
>```
>
>* 按键修饰符
>
>  ```js
>  //抬起键盘
>  @keyup.enter //当用户按的是回车键键时触发
>  @keyup.esc	//当用户按的是esc键时触发
>  //按下键盘
>  @keydown.enter
>  @keydown.esc
>  //keyup和keydown都是事件  enter和esc是修饰符
>  ```
>
>  更多修饰符：https://cn.vuejs.org/v2/guide/events.html

## 5.v-model

* 基本用法

>目标：==value属性==和==Vue变量==,双向绑定到一起
>
>语法: v-model="Vue数据变量"  和v-bind有点像  不过v-model只绑定表单的value属性
>
>双向数据绑定
>变量变化->视图自动同步    视图变化->变量自动同步
>
>v-model暂时只能用在表单标签上。

<img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230325205608322.png" alt="image-20230325205608322" style="zoom:67%;" />

* 修饰符

  >.number  以parseFloat转成数字
  >
  >.trim	类型去除首尾空白字符
  >
  >.lazy	在change时触发而非inupt时  (失去焦点时再将表单值赋给变量)
  >
  >```js
  >v-model.number="变量" //会将表单中的value转为数字再赋给变量
  >```

## 6.v-text和v-html

> 目标：更新DOM对象的innerText/innerHTML
>
> 语法：v-text/v-html = "变量" 
>
> 注意：会覆盖插值表达式

## 7.v-show和v-if

>目标：控制标签的隐藏或出现
>
>语法：
>	v-show="Vue变量"
>	v-if="Vue变量"
>
>原理：
>	v-show：采用display：none 
>	v-if：采用从DOM树上直接移除
>
>高级v-else、v-else-if：
>```vue
><p v-if="age > 18">成年了</p>
><p v-else>未成年</p>
>```

## 8.v-for

>目标：列表渲染，==所在标签==结构按照数据数量,循环生成
>
>语法：
>	v-for="(值变量,索引变量) in 目标结构"
>	v-for="值变量 in 目标结构"
>
>目标结构:	
>	可以遍历数组/对象/数字
>
>遍历对象：
>	v-for="(value，key) in 对象"  key、value随便起，前面是value 后面是key
>
>遍历数字：
>	v-for="i in 10"  i=1、2、3....
>
>注意：
>	v-for的临时变量名不能用到v-for范围外

# Vue基础

##1. 更新监测, key作用

### v-for更新检测

>情况1：数组翻转	更新
>情况2：数组截取	不更新  不改变原数组
>情况3：更新值		不更新
>口诀：用数组内部的方法改变数组，就会导致v-for更新，页面更新。而手动改变数组的值则不会更新
>
>数组非变更方法是返回新数组，这样不会导致v-for更新，可以采用覆盖原数组或this.$set()的方法来使得v-for同步更新
>```js
>this.$set(this.arr, 0, 22) //更新的目标结构  更新的位置  更新的值
>this.arr[0] = 22//效果相同  但这种方法不会同步更新
>```

### v-for更新流程、虚拟DOM

### key的作用

##2. 过滤器

>目标:转换格式,过滤器就是一个函数,传入值返回处理后的值
>
>过滤器只能用在,==插值表达式==和==v-bind动态属性==里
>
>```vue
><div>{{ msg | toUp }}</div>  <!--用'|'分割 msg是变量名 也就是需要过滤的元素  toUp是过滤器名-->
><p :title="msg | toUp"></p> <!--可以用'|'分割多个过滤器-->	
>```
>
>语法：
>```js
>Vue.filter("过滤器名", (值)=>{return “返回处理后的值"})
>filters:{
>    过滤器名字:(值)=>{return "返回处理后的值"}
>}
>//方式一
>Vue.filter("toUp", function(val) {
>  return val.toUpperCase()
>})//写在main.js中 这是个全局过滤器 任何地方都能使用 filter是Vue类的静态方法 添加过滤器
>//第一个参数是过滤器名  第二个参数是方法，输入过滤前的值，输出过滤后的值                         
>                         
>//方式二 局部过滤器  在某个vue文件中写 只能在当前文件中使用                         
>export default{
>    filters:{
>        toUp(val) { //可以传多个参数
>            return val.toUpperCase()
>        }
>    }              
>}
>```

##3. 计算属性

### 基础

>一个变量的值,依赖另外一些数据计算而来的结果  当依赖的数据改变时，这个变量也会跟着改变
>将这个变量定义为方法，方法名为变量名，方法返回值为变量的值
>
>```js
>export default{
>	computed: {//由于computed中定义的也是变量 因此不能和data中的变量重名
>		num() {//num这个方法名就是一个变量名
>			return x + y;//x和y定义在data中   当x和y变化时，num也会动态变化
>        }
>    }//计算属性可以当作普通方法使用      
>}
>```

### 缓存

>计算属性相对于普通方法优势:带缓存
>计算属性对应函数执行后，会把return值缓存起来
>依赖项不变，多次调用都是从缓存取值
>依赖项值-变化，函数会"自动"重新执行-并缓存新值

### 计算属性完整写法

>何时用计算属性完整写法?
>	给计算属性变量赋值时，比如计算属性被v-bind绑定到一个属性上。
>
>set函数和get函数什么执行?
>	set接收要赋予的值
>	get里要返回给这个计算属性具体值
>
>```js
>computed: {
>	num: {//num被当作一个对象
>        set(val) {}//val是接收到的新值
>        get(){
>            return xxx
>        }
>    }
>}
>```

##4. 侦听器

### 基本用法

>目标:可以侦听data/computed属性值的改变
>
>语法：
>```js
>watch: {
>	"被侦听的属性名"(newVal, oldVal) {
>		//侦听这个属性 这个属性值改变了 这个方法会自动触发
>        //可以对旧值和新值做一些操作
>        //可以侦听data和computed里定义的属性
>	}
>}
>```

### 深度侦听和立即执行

>目标:侦听复杂类型,或者立即执行侦听函数
>
>侦听一个对象的时候用这种写法
>
>```js
>watch: {
>	"被侦听的属性名": {//下面三个属性和方法都是固定写法
>        handler(newVal, oldVal){},//侦听的对象改变后会执行
>        deep: true,//是否深度侦听 如果为false 则只有引用的指向改变后才会执行handler 如果为true 对象中的属性变化就					会触发handler
>        immediate: true//是否在网页一开始就执行handler
>    }
>}
>```

# Vue组件

## 1.Vue组件概念,创建和使用

###创建组件

>创建一个独立的vue文件

### 引入组件

>```js
>import 组件对象 from '组件路径'
>```
>
>

### 注册组件

>* 全局注册
>
> ```js
> import 组件对象 from '组件路径'
> Vue.component("组件名", 组件对象)
> ```
>
>* 局部注册(多用)
>
> ```js
> import 组件对象 from '组件路径'
> export default {
> 	components: {
> 		组件名: 组件对象 //这里自定义的组件名就可以当做标签使用了
>          组件名 	//如果组件名和组件对象同名 可简写 只写组件名
>     }
> }
> 
> //局部注册的组件在其子组件中不可用。例如，如果你希望 ComponentA 在 ComponentB 中可用，则你需要这样写：
> var ComponentA = { /* ... */ }
> 
> var ComponentB = {
>   components: {
>     'component-a': ComponentA
>   },
>   // ...
> }
> ```

### 使用组件

>组件名当标签使用

### 组件中的this(个人理解)

>组件方法中的this是一个vue实例，并且这个vue实例不是组件实例
>
>```vue
><!--Son.vue-->
><template>
>  <div></div>
></template>
>
><script>
>export default { //向外暴露的是个组件对象而不是vue对象  
>  methods: {
>    funs() {
>      console.log(this);//这里的this是一个vue实例 而不是组件实例
>    }			
>  }
>}
>```
>
>```vue
><!--father.vue-->
><template>
>  <div>
>    <Son></Son><!--当父组件使用Son组件时，import的组件对象会映射到一个vue对象上(render)，然后将这个vue 对象挂载到
>				<Son>标签上(mount、el)  此时这个<Son>标签会变成<div>标签 所以Son中的方法调用时this是一个vue对				象-->
>  </div>
></template> 
>
><script>
>//这里的Son对象是组件对象而不是vue对象  而funs中的this是个vue对象
>import Son from './components/Son.vue' 
>
>export default {
>  components: {
>    Son: Son,
>  },
>  methods: {
>    funf() {
>      console.log(this);
>    }
>  }
>}
>```
>
>

### scoped属性

>```vue
><style scoped>
></style>
>```
>
>Vue组件内样式，只针对当前组件内标签生效如何做?
>	给style上添加scoped
>
>原理和过程是什么?
>	会自动给标签添加data-v-hash值属性,所有选择都带属性选择
>
><img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230326155028691.png" alt="image-20230326155028691" style="zoom:50%;" />![image-20230326155049454](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230326155049454.png)

## 2.Vue组件通信

### 父传子

>在父引入子，被引入的是儿子
>
>父组件给子组件传值步骤：
> 1. 子组件的props中准备好变量准备接收
>
>    ```js
>    export default {
>    	components: {
>    		props: ['title', 'price', 'info']//引号里的都是自定义的变量名 父组件给这些变量传值
>        }
>    }
>    ```
>
> 2. 父组件内使用子组件，子组件准备接收的变量在子组件的标签中以属性的形式表现
>
>    ```vue
>    <Son title="xxx" price="xxx" v-bind:info="xxx"></Son>
>    <!--父组件可以在子组件的标签中添加属性来传值  属性名为子组件定义的变量名-->
>    ```
>
>可以配合v-for使用
>```vue
><Son v-for="item in arr" :title="item" :price="item" v-bind:info="item"></Son>
>```

### 单向数据流

>从父到子的数据流向,叫单向数据流。
>
>Vue规定props里的变量,本身是只读的。但是子组件改变数据不通知父组件会造成数据不一致性。

### 子传父(可以传多个参数)

>* 父：
>
> ```vue
> <Son v-on:自定义事件名="fun"></Son> <!--父组件在子组件标签自定义事件 当子组件触发这个事件时父组件
> 									可以通过后面定义的fun方法中的参数获取子组件传的值-->
> <script>
> export default {
> 	method: {
> 		fun(int index) {
>             //处理
>             //子组件可以通过这个方法的参数来给父组件传值
>         }
>     }
> }
> </script>
> ```
>
>* 子：
>
> ```vue
> <script>
> export default {
> 	method: {
> 		subFun() {
>             this.$emit("自定义的事件名", index)//这个语句可以触发子组件标签中的自定义事件 inedx是子传给父的数据
>         }
>     }
> }
> </script>
> ```
>
>官网讲解：
>
>有的时候用一个事件来抛出一个特定的值是非常有用的。这时可以使用 `$emit` 的第一个参数之后的参数(可以传多个参数)来提供这个值：
>
>```vue
><button v-on:click="$emit('enlarge-text', 1)">
>  Enlarge text
></button><!--子组件  按下这个按钮会触发自定义事件'enlarge-text'并且传递一个参数1-->
>```
>
>然后当在父级组件监听这个事件的时候，我们可以通过 `$event` 访问到被抛出的这个值：
>
>```vue
><blog-post v-on:enlarge-text="postFontSize += $event"></blog-post>
><!-- blog-post是子组件标签名 "enlarge-text"是自定义事件名  $event是接收到的值1 -->
>```
>
>或者，如果这个事件处理函数是一个方法：
>
>```vue
><blog-post v-on:enlarge-text="onEnlargeText"></blog-post>
><!--"enlarge-text"是自定义事件名  onEnlargeText是处理函数 则这个方法的参数就是传来的值1-->
><script>
>    methods: {
>      onEnlargeText: function (enlargeAmount) { enlargeAmount == 1
>        this.postFontSize += enlargeAmount
>      }
>    }
></script>    
>```
>
>

### 跨组件通信-EventBus

![image-20230326165503173](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230326165503173.png)

>步骤：
>
>1. src/EventBus/index.js 一创建空白Vue对象并导出命名为eventBus
>
>2. 在要接收值的组件(List.vue)     eventBus.$on('事件名'，函数体)  监听事件
>
>   ```vue
>   <script>
>         
>   import eventBus from '../EventBus/index.js'
>   export default {
>     data() {
>       return {
>         num: 999
>       }
>     },
>     created() {
>       eventBus.$on('send', (par)=> { //这里要用箭头函数  不能用匿名函数
>         console.log(eventBus);	//因为匿名函数的调用者是eventBus this.num是eventBus中的属性
>         console.log(this);	//而箭头函数没有调用者 它会捕获其所在（即定义的位置）上下文的this值
>         this.num = par		//这里要修改到这个组件的num 只能用箭头函数
>       })
>     },
>   }
>   </script>
>   ```
>
>   
>
>3. 在要传递值的组件(MyProduct.vue)       eventBus.$emit('事件名'，值)  触发事件
>
>   ```vue
>   <script>
>   import eventBus from '../EventBus/index.js'
>   export default {
>     methods: {
>       fun1() {
>         eventBus.$emit('send', 520) //触发'send'这个事件  这个事件是属于eventBus对象的，其他vue对象不能响应
>       }
>     }
>   }
>   </script>
>   ```
>
>   

## 3.动态组件

>```vue
><component v-bind:is="组件名"></component>
>```
>
>用<component>标签表示一个未知组件，通过属性is来绑定组件名，可以动态地改变组件

## 4.Vue生命周期

### 钩子函数

>目标: Vue框架内置函数，随着组件的生命周期阶段，自动执行
>
>分类:4大阶段8个方法
>
>|  阶段  | 方法名(阶段前) | 方法名(阶段后) |
>| :----: | :------------: | :------------: |
>| 初始化 |  beforeCreate  |    created     |
>|  挂载  |  beforeMount   |     mouted     |
>|  更新  |  beforeUpdate  |    updated     |
>|  销毁  | beforeDestroy  |   destoryed    |

### 初始化

>1. new Vue() -Vue实例化(组件也是一个小的Vue实例)
>
>2. lnit Events & Lifecycle -初始化事件和生命周期函数
>
>3. beforeCreate -生命周期钩子函数被执行
>
>4. lnit injections&reactivity - Vue内部添加data和methods等
>
>5. created-生命周期钩子函数被执行,实例创建
>
>6. 接下来是编译模板阶段-开始分析
>
>7. Has el option?-是否有el选项-检查要挂到哪里
>   没有.调用$mount()方法
>   有,继续检查template选项
>
>   <img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230328150410140.png" alt="image-20230328150410140" style="zoom:50%;" />

### 挂载

>1. template选项检查
>  有–编译template返回render渲染函数
>     无-编译el选项对应标签作为template(要渲染的模板)
>
>2. 虚拟DOM挂载成真实DOM之前
>
>3. beforeMount-生命周期钩子函数被执行
>
>4. Create ...把虚拟DOM和渲染的数据一并挂到真实DOM上
>
>5. 真实DOM挂载完毕
>
>6. mounted -生命周期钩子函数被执行
>
>   <img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230328151216261.png" alt="image-20230328151216261" style="zoom:50%;" />

### 更新

>1. 当data里数据改变,更新DOM之前
>
>2. beforeUpdate -生命周期钩子函数被执行
>
>3. Virtual DOM......虚拟DOM重新渲染,打补丁到真实DOM
>
>4. updated -生命周期钩子函数被执行
>
>5. 当有data数据改变–重复这个循环
>
>   <img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230328151537623.png" alt="image-20230328151537623" style="zoom:50%;" />

### 销毁

>1. 当$destroy()被调用一比如组件DOM被移除(例v-if)
>
>2. beforeDestroy -生命周期钩子函数被执行
>
>3. 拆卸数据监视器、子组件和事件侦听器
>
>4. 实例销毁后,最后触发一个钩子函数
>
>5. destroyed -生命周期钩子函数被执行
>
>   <img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230328151749038.png" alt="image-20230328151749038" style="zoom:50%;" />

## 5.axios使用

### 介绍

>官网：http://www.axios-js.com/
>
>特点：
>
>* 支持客户端发送Ajax请求
>* 支持服务端Node.js发送请求
>* 支持Promise相关用法
>* 支持请求和响应的拦截器功
>* 能自动转换JSON数据
>
>axios底层还是原生js实现,内部通过Promise封装的。基于原生ajax+ Promise技术封装通用于前后端的请求库

### 使用

>```js
>axios({
>    method:'请求方式',
>    url: '请求地址',
>    data: {	//拼接到请求体的参数，post请求的参数
>        xxx:XXX,
>    },
>    params: {	//拼接到请求行的参数，get请求的参数
>    	xxx: XXX
>    }
>}).then(res => {
>    console.log(res.data)//后台返回的结果
>}).catch(err => {
>    console.log(err)
>})//后台报错返回
>```
>
>
>
>axios函数调用原地结果是什么?
>	是一个Promise对象
>
>如何拿到Promise里ajax的成功或失败的结果?
>	then() / catch()
>
>axios哪个选项,可以把参数自动装入到请求体中?
>	data选项
>
>axios默认发给后台请求体数据格式是?
>	json字符串格式

### axios全局配置

>目标：配置基础地址,统一管理  可在官网看到许多配置
>
>```js
>axios.defaults.baseURL = "http://127.0.0.1:3306";
>```
>
>发请求时url就不用带基地址了

## 6.$refs和$nextTick使用

### 获取原生DOM的两种方式

>```vue
><h1 ref="myH1" id="h"><h1>
><script>
>	export default{
>        mounted() {
>            document.getElementById("h")//第一种方式
>            this.$refs.myH1;//第二种方式
>        }
>    }    
></script>
>```

### 获取组件对象

>```vue
><son ref="demo"></son>
><script>
>	export default{
>        mounted() {
>            this.$refs.demo;//获取到了组件对象 是组件对象转换后的vue对象
>        }
>    }    
></script>
>```

### 获取原生DOM的内容

>```vue
><p ref="a">{{ count }}</p>
><button @click="btn">加1</button>
>
><script>
>	export default{
>        methods: {
>            btn() {
>                this.count++;
>                this.$refs.a.innerHTML;//不会获得count改变后的值  
>            }//因为Vue更新DOM是异步的 会将DOM的更新任务放到队列中等主线程执行结束后才获取
>        }	//要想获得更新后的值可以在updated钩子函数中获得 也可以通过$nextTick获得
>    }    
></script>
>```
>
>```js
>export default{
>    methods: {
>        btn() {
>            this.count++;
>            this.$nextTick(() => {
>              	//DOM更新后会挨个触发nextTick函数 
>                this.$refs.a.innerHTML;
>            })
>            console.log("你好")//这行代码先于nextTick执行
>        }
>    }	
>}    
>```

# Vue组件-进阶

## 1.Vue组件进阶

### 动态组件

>目标：多个组件使用同一个挂载点，并动态切换
>
>```vue
><component v-bind:is="组件名"></component>
>```
>
>用<component>标签表示一个未知组件，通过属性is来绑定组件名，可以动态地改变组件
>
>原理是频繁地创建和销毁组件对象

### 组件缓存

>Vue内置的keep-alive组件包起来要频繁切换的组件
>
>```vue
><keep-alive>
>	<component v-bind:is="组件名"></component>
></keep-alive>
>```

### 组件激活和非激活

>目标：扩展2个新的生命周期方法  组件被缓存时会多出这两个钩子函数
>	activated ：激活时触发
>	deactivated：失去激活状态触发
>
>当组件被缓存时，在组件之间切换，如果当前组件被激活或失去激活就会执行这两个方法

### 组件插槽

>
>通过slot标签,让组件内可以接收不同的标签结构显示。场景：当组件内某一部分标签不确定时。
>
>给组件插入什么标签,组件就显示什么标签。
>
>语法：
>	1. 组件内用<slot></slot>占位
>	1. 使用组件时<组件名> </组件名>夹着的地方,传入标签替换slot
>
>设置插槽默认内容：
>    <slot>内放置内容,作为默认显示内容。当父组件不给<slot>传内容时会用默认内容。
>
###命名插槽

>一个组件有两个插槽怎么处理：
>	使用slot的name属性命名。template配合v-slot对应slot。
>
>```vue
><slot name="title"></slot>
><slot name="content"></slot>
>
><!--使用     v-slot可以简写为#-->
><son>
>    <template v-slot:title>插槽的内容</template>
>    <template #:content>插槽的内容</template>
></son>
>```
>

### 作用域插槽

>目标：使用插槽时,想使用子组件内变量。
>
>1. 子组件,在slot上绑定属性和子组件内的值
>2. 使用组件,传入自定义标签，用template和v-slot="自定义变量名"
>3. 自定义变量名自动绑定slot上所有属性和值
>
>```vue
><div>
>	<slot :row="defaultObj">{{ defaultObj.defaultOne }}</slot>
>    <!--defaultObj是这个组件的一个data-->
></div>
>
><!--使用-->
><son>
>    <template v-slot="obj">{{ obj.row.defaultTwo }}</template>
>	<!--obj是一个对象  obj = {row : defaultObj}-->
></son>
>```
>
>

## 2.自定义指令

# 路由系统

##1.Vue路由简介和基础使用

###前端路由

>目标：路径和组件的映射关系
>
>![image-20230328185135913](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230328185135913.png)

### 为什么要使用路由

>具体使用示例:网易云音乐https://music.163.com/
>
>单页面应用(SPA):所有功能在一个html页面上实现
>
>前端路由作用:实现业务场景切换

### 路由使用

>官网: https://router.vuejs.org/zh/
>
>提供了两个内置的全局组件
>```vue
><router-link to="/"></router-link>  <!--RouterLink组件-->
><router-view></router-view>	<!--RouterView组件-->
>```

### 组件的分类

>组件分为==页面组件==和==复用组件==。
>	src/views文件夹	页面组件–页面展示–配合路由使用
>	src/components文件夹	复用组件–展示数据/常用于复用 重复渲染结构一样的标签
>
>.vue文件本质无区别,方便大家学习和理解,总结的一个经验。

### vue-router的使用

>1. 下载vue-router模块到当前工程
>  ```bash
>  yarn add vue-router
>  ```
>
>2. 在main.js中引入VueRouter函数
>  ```js
>  import VueRouter form 'vue-router'
>  ```
>
>3. 添加到Vue.use()身上–注册全局RouterLink和RouterView组件
>  Vue.use()是用来使用插件的。插件通常用来为Vue添加全局功能。
>   这里是为了添加两个全局组件。RouterLink组件和RouterView组件。
>
>  ```js
>  Vue.use(VueRouter)
>  ```
>
>4. 创建路由规则数组：路径和组件名对应关系
>   ```js
>   const routes = [
>   	{
>   		path: '/find',
>   		component: Find
>   	},
>   	{
>   		path: '/my',
>   		component: My
>   	},
>   	{
>   		path: '/part',
>   		component: part
>   	}
>   ]
>   ```
>
>5. 用规则生成路由对象
>   ```js
>   const router = new VueRouter({
>       //routes: routes //routes是固定的key(传入规则数组)
>   	routes  //同名 可以简写
>   })
>   ```
>
>6. 把路由对象注入到new Vue实例中
>   ```js
>   new Vue({
>       //router: router
>       router, //简写  
>       render: h => h(App)
>   }).$mount("#app")
>   ```
>
>7. 用router-view作为挂载点,切换不同的路由页面
>   当url的hash值路径切换，显示规则里对应的组件到<router-view>处
>
>   ```vue
>   <template>
>       <div>
>           <div class="footer_wrap">
>               <a href="#/find">发现音乐</a>
>               <a href="#/my">我的音乐</a>
>               <a href="#/part">朋友</a>
>       	</div>
>           <div class="top">
>           	<!--设置挂载点  当url的hash值路径切换，显示规则里对应的组件到这-->
>           	<router-view></router-view>
>       	</div>
>       </div>
>   </template>
>   ```

##2.声明式导航

>目标：可用组件router-link来替代a标签
>
>1. vue-router提供了一个全局组件router-link。本质就是a标签。
>
>2. router-link实质上最终会渲染成a链接 to属性等价于提供href属性(to无需#)
>
>3. router-link提供了声明式导航高亮的功能(自带类名)  
>   就是被点击的route-link会被添加一个类名，程序员可以根据需要对这个类名添加样式(如高亮)
>
>   ```vue
>   <div class="footer_wrap">
>       <router-link to="/find">发现音乐</router-link>
>       <router-link to="/my">我的音乐</router-link>
>       <router-link to="/part">朋友</router-link>
>   </div>
>   ```

### 跳转传参

>目标:在跳转路由时,可以给路由对应的组件内传值
>
>第一种方式：
>在router-link上的to属性传值,语法格式如下
>	/path?参数名=值
>
>对应页面组件接收传递过来的值$route.query.参数名
>
>```vue
><router-link to="/find?name=vanyan">发现音乐</router-link>
><router-view></router-view>
>
><div>
>    {{ this.$route.query.name }} <!--这个this是vue对象  $route是vue的内置属性  是当前的路径对象-->
></div>
>```
>
>
>
>第二种方式：
>在路由规则path数组上定义 /path/:参数名
>to="/path/值"
>
>```js
>const routes = [
>    {
>        path: '/find',
>        component: Find
>	},
>    {
>        path: '/find/:name/:age',
>        component: Find
>    }
>]  //定义规则
>```
>
>```vue
><router-link to="/find/vanyan/16">发现音乐</router-link>  <!--传值-->
>```
>
>```js
>this.$route.params.name
>this.$route.params.age   //接收值
>```

##3.重定向和模式

### 路由重定向

>目标:匹配path后,强制跳转path路径
>
>网页打开url默认hash值是/路径
>redirect是设置要重定向到哪个路由路径
>
>```js
>const routes = [
>    {
>        path: '/',
>        redirect: '/find'  //重定向到/find
>    },
>    {
>        path: '/find',
>        component: Find
>	},
>    {
>        path: '/find/:name/:age',
>        component: Find
>    }
>]
>```

###路由404

>```js
>import NotFound from '@/views/NotFound'
>const routes = [
>    .....
>    {//一定要写在数组最后  这样前面路由匹配不到的就会到404这里
>    	path: '*'
>    	component: NotFound
>    }
>]
>```

### 路由模式

>目标:修改路由在地址栏的模式
>hash路由例如:http://localhost:8080/#/home
>history路由例如: http://localhost:8080/home (以后上线需要服务器端支持,否则找的是文件夹)
>
>```js
>const router = new VueRouter({
>    mode: "history",  //默认是hash模式
>	routes  
>})
>```

##4.编程式导航

### 用JS代码来进行跳转

>语法：
>```js
>this.$router.push({
>    	path: "路由路径"
>})
>//或
>this.$router.push({
>    	name: "路由名"
>})
>const routes = [
>    {
>        path: '/find',
>        name: 'Find', //路由名在这
>        component: Find 
>    }
>]
>```

### JS路由跳转传参

>```js
>this.$router.push({
>        path: "路由路径",
>        name: "路由名",
>        query: {
>            "参数名": 值
>        },
>        params: {
>            "参数名": 值
>        }
>})
>```
>
>```vue
><!--注意  使用path会忽略params  name不会-->
><div>
>	<p>
>            {{ $route.query.name }}
>            ----
>            {{ $route.params.name }}
>    	</p>
></div>
>```
>

##5.嵌套和守卫

### 嵌套路由

>1. 创建所需要的组件
>
>2. main.js 配置二级路由
>
>   * —级路由path从/开始定义
>   * 二级路由往后path直接写名字,无需/开头
>   * 嵌套路由在上级路由的children数组里编写路由信息对象
>
>   ```js
>   {
>       path: "/find",
>       component: Find,
>       children:[
>       	{
>       		path: "ranking", //二级路由不用带'/'
>               component: Ranking
>           },
>       	{
>       		path: "recommend",
>                component: Recommend
>           },
>       	{
>       		path: "songlist",
>                component: SongList
>           }
>       ]
>   }
>   ```
>
>3. 说明：
>
>   App.vue的router-view负责发现音乐和我的音乐页面(一级路由),切换
>
>   Find.vue的的router-view负责发现音乐下的,三个页面(二级路由),切换
>
>   ![image-20230329110247311](D:\Program Files (x86)\vanyan\Typora\imgs\image-20230329110247311.png)

###声明式导航-类名区别

>router-link-exact-active (精确匹配)url中hash值路径,与href属性值完全相同，设置此类名
>router-link-active (模糊匹配)url中hash值,包含href属性值这个路径
>
><img src="D:\Program Files (x86)\vanyan\Typora\imgs\image-20230329110820181.png" alt="image-20230329110820181" style="zoom:67%;" />

### 路由守卫

>场景：当你要对路由权限判断时。
>
>```js
>const router = new VueRouter({
>    routes
>})
>
>//路由跳转之前先调用这个函数
>//参数一：目标路由
>//参数二：来源路由
>//参数三：调用next()会让路由正常跳转，调用next(false)会在原地停留
>//next('强制跳转到另一个路由上') 如果不使用next函数则会停在原地
>router.beforeEach((to, from, next) => {
>  console.log(v.$route)
>  console.log(to);  //to from 和 $route是一个类型的对象 vue内部的一个属性
>  console.log(from);
>  console.log(next);//next是一个函数
>})
>```
