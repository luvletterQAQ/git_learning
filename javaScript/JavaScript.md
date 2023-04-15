js权威网站：https://developer.mozilla.org/zh-CN/docs/Web/JavaScript

MDN网站

# 一、基础语法

## 1. JS介绍

### JS组成

* ECMAScript

  规定了js基础语法核心知识。比如:变量、分支语句、循环语句、对象等等

* Web APIs

  DOM操作文档，比如对页面元素进行移动、大小、添加删除等操作
  BOM操作浏览器，比如页面弹窗，检测窗口宽度、存储数据到浏览器等等![image-20230323121806360](D:\Program Files (x86)\王岩\Typora\imgs\image-20230323121806360.png)

### JS书写位置

* 内部js

  ```html
  <body>
      <!--html标签....-->
      <!-- 内部js  写在body的最底部
  	我们将<script>放在HTML文件的底部附近的原因是浏览器会按照代码在文件中的顺序加载HTML。如果先加载的JavaScript期望修改其		下方的HTML，那么它可能由于HTML尚未被加载而失效。因此，将JavaScript代码放在HTML页面的底部附近通常是最好的策略。
  	-->
      <script>
          alert('你好，js')
      </script>
  </body>
  ```

* 外部js

  ```html
  <body>
      <!--外部js-->
      <script src="./js/my.js">
      	//里面无需写内容  写了的东西会自动忽略
      </script>
  </body>
  ```

* 内联js

  ```html
  <body> 
      <button onclick="alert('逗你玩')">点我</button>
      <!--此处作为了解即可，但是后面vue框架会用这种模式-->
  </body>
  ```

### JS输入输出

```html
<script>
    document.write('hello')
    document.write('<h1>hello</h1>')	//直接在页面上输出
    alert('弹出内容')
    consloe.log('控制台打印', 250)
    //输入语句  页面显示一个对话框提示用户输入信息
    prompt('请输入你的年龄:')
</script>
```

![image-20230323125549182](D:\Program Files (x86)\王岩\Typora\imgs\image-20230323125549182.png)

js执行顺序：

* 按HTML文档流顺序执行JavaScript代码
* alert()和 prompt()它们会跳过页面渲染先被执行（目前作为了解，后期讲解详细执行过程)

## 2.变量

### 变量的基本使用

1. 变量声明

   ```js
   let 变量名
   ```

2. 变量赋值

3. 变量声明时直接赋值

### 变量命名规范

1. 规则：
   * 不能用关键字
   * 只能用==下划线==、字母、数字、==$==组成，==数字不能开头==
   * 严格区分大小写
2. 规范
   * 遵循小驼峰命名

### var和let区别

在较旧的JavaScript，使用关键字var来声明变量，而不是let。var现在开发中一般不再使用它，只是我们可能再老版程序中看到它。let为了解决var的一些问题。																															

* 可以先使用再声明(不合理)
* var声明过的变量可以重复声明(不合理)
* 比如变量提升、全局变量、没有块级作用域等等

### 数组

```js
let arr = []//数组字面量
let arr = [1, 2, 3]
arr.length //len

//数组的map方法
const newArr = arr.map(function(item, index) {
    //item是数组里的值
    //index是下标
    //这里可以处理数组元素
    return item + '!';//return可以将数组中的数据处理过后作为map方法的返回
})

//数组join方法
arr.join('-') //参数是分隔符  1-2-3
```

## 3.常量

* 使用const声明 

  ```js
  const PI = 3.14159
  ```

* 声明的时候必须赋值

## 4.数据类型

js是弱数据类型语言，只有给变量赋值之后才能确定变量是哪种类型

### 基本数据类型

1. number

   NaN代表一个计算错误。它是一个不正确的或者一个未定义的数学操作所得到的结果。

   NaN是粘性的。任何对NaN的操作都会返回NaN。NaN和任何类型做操作结果都是NaN。

2. string

   * 字符串表示

     双引号、单引号、反引号(``)都可以，推荐使用单引号。

     单引号/双引号可以互相嵌套，但是不以自已嵌套自已(口诀:外双内单，或者外单内双)。

     ```js
     console.log('他的名字叫"老明"')
     console.log("他的名字叫'老明'")
     console.log('他的名字叫\'老明\'')
     ```
     
   * 字符串拼接用 '+' 号

     ```js
     let age = 16
     document.write('我今年' + age + '岁')
     document.write(`我今年${age}岁了`) //模板字符串  必须用反引号(``)包围
     ```

* boolean

* undefined

  只声明不赋值的变量就是undefined

* null

  null和undefined区别: undefined表示没有赋值。null表示赋值了，但是内容为空。

  ```js
  console.log(undefined + 1)//NaN
  console.log(null + 1)//1
  ```

### 引用类型

* object对象

### 检测数据类型

```js
typeof x //常用
typeof(x)
```

## 5.数据类型的转换

==表单、prompt获取的数据默认是字符串型==

### 隐式转换

* +号两边只要有一个是字符串，都会把另外一个转成字符串。

* 除了+以外的算术运算符比如–  * /等都会把数据转成数字类型。

  ```js
  let age = +'16';//age是Number型
  ```

### 显示转换

* 转为数字型（Number、parseInt、parseFloat）

  ```js
  let str = '16'
  
  let age = Number(str)
  age = Number('hello') //NaN  NaN也是Number类型 代表非数字
  
  parseInt(数据)//只保留整数
  parseInt('12px')//12
  parseInt('12.66px')//12
  parseInt('abc12px')//NaN
  
  parseFloat(数据)//可以保留小数
  parseFloat('12px')//12
  parseFloat('12.66px')//12.66
  parseFloat('abc12.66px')//NaN
  ```

# 二、流程控制

## 1.运算符

### 比较运算符

* ==：比较值
* ===：比较值和类型
* !=
* !==
* 注：NaN不等于任何值包括它本身。
* 不同类型之间比较会发生隐式转换。最终会把数据隐式转换成number类型再比较。

### 逻辑运算符

### 	==注：==js中只有&&没有&

### 运算符优先级

![image-20230323154115077](D:\Program Files (x86)\王岩\Typora\imgs\image-20230323154115077.png)

## 2.语句

### 分支语句

1. if语句

   除了0所有的数字都为true。除了空字符串所有字符串都为true。

2. 三元运算符

3. switch语句

### 循环语句

# 三、函数

## 1.函数声明

```js
function 函数名(){函数体}
```

## 2.函数传参

* 实参个数和形参个数可以不一样，形参过多，则形参的默认值是undefined。实参过多会忽略多出来的部分。

* 可以指定默认值

  ```js
  function 函数名(x = 0, y = 0) {
      //...
  }
  ```

## 3.函数返回值

```js
function 函数名(x = 0, y = 0) {
    return x + y;
}
```

## 4.匿名函数

```js
function() {}
```

### 函数表达式

将匿名函数赋值给一个变量，并且通过变量名称进行调用我们将这个称为函数表达式。

```js
let fn = function(){}
fn()//调用
```

函数表达式和普通函数的区别：普通函数的调用可以在函数声明之前，但函数表达式不可以，因为函数表达式赋给了let声明的变量。

### 立即执行函数

```js
//作用是避免全局变量之间的污染(全局变量过多 没有可用的变量名)  立即执行函数内的变量被看作局部变量  但函数是立即执行的
//多个立即执行函数之间用分号分割
//第一种方法
(function(x, y){
    console.log(x + y)
})(1, 2)
//第二种方法
(function(x, y){
    console.log(x + y)
}(1, 2))
//第三种方法 前面加!或+
!function(x, y){
    console.log(x + y)
}(1, 2)
```

# 四、对象

## 1.对象使用

对象和数组的实例也是存储在堆中，和java一样。const修饰的对象和数组可以改变实例的值但不能改变引用的值。

```js
let 对象名 = {} //{}是对象字面量
let 对象名 = new Object()
let 对象名 = {
    属性名: 属性值,
    方法名: 函数
}
let person = {
    name: 'ming',
    age: 16,
    gender: '女'
}
person.hobby = '足球' //可以给对象添加属性
delete person.hobby //删除属性

let person = {
    'p-name': 'ming', //属性加双引号可以在属性名中加其他符号
    age: 16,
    gender: '女',
    funName: function() {
        consloe.log('这是一个方法')
    } //方法的定义 匿名函数  person.funName()
    funName() {
        consloe.log('这是一个方法')
    }//第二种定义方法
}
person['p-name']//这样使用  因为'-'是违规字符，因此不能用person.p-name的形式  方法名同理
```

## 2.遍历对象

```js
let person = {
    'p-name': 'ming', 
    age: 16,
    gender: '女',
}
//一般不用这种方式遍历数组，因为这种方式是遍历键值对的，k默认是字符串，而遍历数组是k是数组下标
//遍历对象时k是属性名，并且由于k是字符串，因此只能通过person[k]来获取值，而不能使用person.k
for (let k in person) {
    console.log(k)
    console.log(person[k])
}
```

## 3.内置对象

* Math

  ```js
  Math.random() //[0, 1)
  console.log(Math.floor(Math.random() * 11)))//0-10之间的整数
  ```

# 五、DOM获取&属性操作

## 1.DOM

### 介绍

DOM是浏览器提供的一套专门用来操作网页内容的功能。

### 作用

开发网页内容特效和实现用户交互。可以通过DOM动态地改变html标签的内容。

### DOM树

* 作用：文档树直观的体现了标签与标签之间的关系

  ![image-20230323191141965](D:\Program Files (x86)\王岩\Typora\imgs\image-20230323191141965.png)

### DOM对象

* 介绍

  浏览器根据html标签生成的JS对象，所有的标签属性都可以在这个对象上面找，修改这个对象的属性会自动映射到标签身上。在html是个标签，到JS里获取后就是个对象。

* 核心思想

  把网页内容当作对象来处理。这些对象是浏览器根据html标签自动生成的。

* document对象

  是DOM里提供的一个对象，网页所有的内容都在document里面，她所提供的属性和方法都是用来访问和操作网页内容的。如：document.write()。

  ![image-20230323192049183](D:\Program Files (x86)\王岩\Typora\imgs\image-20230323192049183.png)

## 2.获取DOM对象

### 根据CSS选择器来获取DOM元素（重点）

1. 选择匹配的第一个元素   

   ```html
   <body>
   	<div class="box"></div>
       <p id = "nav">导航栏</p>
       <ul>
       	<li>1</li>
           <li>2</li>
       </ul>
       <script>
       	const box = document.querySelector("div") //通过css选择器选
           const box = document.querySelector(".box") //通过类名选
           const nav = document.querySelector("#nav")
           const li = document.querySelector("ul li:first-child")
           console.log(box)
       </script>
   </body>
   ```

2. 选择匹配的多个元素

   ```js
   const box = document.querySelectorAll("div")
   const lis = document.querySelectorAll("ul li")
   //返回的是一个数组 这里的数组是伪数组 不是真正的数组 没有pop() push()方法
   ```

### 其他获取DOM元素的方法（了解）

```js
document.getElementById('nav')
document.getElementsByTagName('div')
document.getElementsByClassName('box')
```

## 3.操作元素内容

### 对象.innerText属性

```html
<div class="box">我是文字的内容</div>
<script>
	const box = document.querySelectorAll(".box")
    box.innerText = '我是一个盒子' //修改文本
    box.innerText = '<strong>我是一个盒子</strong>' //<strong>我是一个盒子</strong>  innerText不会解析标签
</script>
```

### 对象.innerHTML属性

```js
const box = document.querySelectorAll(".box")
box.innerHTML = '<strong>我是一个盒子</strong>' //加粗我是一个盒子 innerHTML会解析标签
```

## 4.操作元素属性

###操作元素*常用*属性

最常见的属性比如: href、title、src等

```js
对象.属性=值
//1.获取元素
const pic = document.querySelector('img')
//2.操作元素
pic.src = './images/xxx.jpg'
pic.title = '主题'
```

###操作元素*样式*属性

* 通过style属性操作CSS

  ```html
  对象.style.样式属性 = 值
  <head>
      <style>
          .box {
              width: 200px;
              height: 200px;
              background-color: pink;
          }
  	</style>
  </head>
  <body>
  	<div class="box"></div>
  	<script>
          //1．获取元素
          const box = document.querySelector('.box')
          //2．修改样式属性  对象.styLe.样式属性=‘值’   别忘了跟单位
          box.style.width = '300px' 
          //多组单词的采取小驼峰命名法
          box.style.backgroundColor = 'hotpink'
          //box.style['background-color'] = 'black'  这样也可以
      </script>
  </body>
  
  ```

* 操作类名(className)操作CSS

  ```js
  //提前写好css样式类名为box  在js中将你要修改的标签的类名改为box即可
  对象.className = 'box'
  ```

* 通过classList操作类控制CSS

  为了解决className容易覆盖以前的类名，我们可以通过classList方式追加和删除类名。

  ```js
  对象.classList.add('类名')
  对象.classList.remove('类名')
  对象.classList.toggle('类名') //切换  有这个类就删除 没有就加上

###操作*表单元素*属性

```js
//1获取元素
const uname = document.querySelector('input')
// 2获取值  获取表单里面的值用 表单.vaLuel 
console.Log(uname. value)/ //获取表单的内容
console.Log(uname.innerHTML) //innertHTML得不到表单的内容
// 3设置表单的值
uname.vaLue ='我要买电脑'
uname.type = 'password'

//表单属性中添加就有效果,移除就没有效果,一律使用布尔值表示 
//如果为true代表添加了该属性如果是false代表移除了该属性
//比如: disabled、 checked、 selected
```

###自定义属性

```html
<body>
    <div data-id="1" data-spm="不知道">1</div>
	<div data-id="2">2</div>
    <div data-id="3">3</div>
	<script>
    	const one = document.querySelector('div')
		console.log(one.dataset.id)// 1   
         //one.dataset获取到了这个标签的全部自定义的属性   以map的方式返回 其中key去掉了前面的'data-'
		console.log(one.dataset.spm) //不知道
	</script>
</body>
```

## 5.定时器-间歇函数

### 开启间歇函数

```js
setInterval(函数名, 间隔时间) //单位是毫秒 函数名可用匿名函数代替
```

### 关闭间歇函数

```js
//间歇函数返回的是一个id数字  这个id是这个定时器的标识  关闭定时器就要根据这个id来关闭
let id = setInterval(函数名, 间隔时间)
clearInterval(id)	//关闭间歇函数
```

# 六、DOM事件基础

## 1.事件监听(绑定)

### 什么是事件

事件是在编程时系统内发生的动作或者发生的事情，比如用户在网页上单击一个按钮。

### 什么是事件监听

就是让程序检测是否有事件产生，一旦有事件触发，就立即调用一个函数做出响应，也称为绑定事件或者注册事件。比如鼠标经过显示下拉菜单，比如点击可以播放轮播图等等。

### 事件监听

* 语法

  ```js
  object.addEventListener('事件类型', 要执行的函数)
  //事件类型要加引号
  ```

* 三要素

  事件源：哪个dom元素被事件触发了，要获取dom元素
  事件类型：用什么方式触发，比如鼠标单击click、鼠标经过mouseover等

  事件调用的函数：要做什么事

### 事件监听版本

* DOM V0

  ```js
  事件源.on事件 = function(){}
  but.onclick = function(){}
  ```

* DOM V2

  ```js
  事件源.addEventListener(事件, 事件处理函数)
  ```

* 区别

  ```js
  //on方式会被覆盖，addEventListener方式可绑定多次，拥有事件更多特性，推荐使用
  btn.oncLick = function () {
      alert(11)
  }
  btn.onclick = function () {
      alert(22)
  }//只会弹出22  覆盖掉了11
  
  btn.addEventListener('click', function () {
      alert(11)
  })
  btn.addEventListener('click', function () {
      alert(22)
  })//先弹出11 再弹出22
  ```

##2.事件类型

### 鼠标事件

* click鼠标点击
* mouseenter鼠标经过
* mouseleave鼠标离开

### 焦点事件(表单获得或失去光标)

* focus获得焦点
* blur失去焦点

### 键盘事件

* Keydown键盘按下触发
* Keyup键盘抬起触发

### 文本事件

* input用户输入事件

  ![image-20230324125554449](D:\Program Files (x86)\王岩\Typora\imgs\image-20230324125554449.png)

##3.事件对象

### 获取事件对象

* 什么是事件对象

  也是个对象，这个对象里有事件触发时的相关信息
  例如:鼠标点击事件中，事件对象就存了鼠标点在哪个位置等信息

* 使用场景

  可以判断用户按下哪个键，比如按下回车键可以发布新闻

  可以判断鼠标点击了哪个元素，从而做相应的操作

* 如何获取

  在事件绑定的回调函数的第一个参数就是事件对象 

  一般命名为event、ev、e

  ```js
  object.addEventListener('click', function(e) {})
  ```

### 事件对象常用属性

* type

  获取当前的事件类型

* clientX/clientY

  获取光标相对于浏览器可见窗口左上角的位置

* offsetX/offsetY

  获取光标相对于当前DOM元素左上角的位置

* key

  用户按下的键盘键的值

  现在不提倡使用keyCode

##4.环境对象

指的是函数内部特殊的变量this ，它代表着==当前函数运行时所处的环境==

```js
//每个函数里面都有this  环境对象  this指向调用这个函数的对象  函数调用方式不同，this指向也不同
//普通函数里面this指向的是window  
function fn() {
	console.log(this)
}
window.fn() //等于fn()
```

##5.回调函数

如果将函数A做为参数传递给函数B时，我们称函数A为回调函数。

# 七、DOM事件进阶

## 1.事件流

### 事件流与两个阶段说明

> 事件流指的是事件完整执行过程中的流动路径。
>
> 说明:假设页面里有个div，当触发事件时，会经历两个阶段，分别是捕获阶段、冒泡阶段。
>
> 简单来说:捕获阶段是从父到子冒泡阶段是从子到父。

![image-20230324144409607](D:\Program Files (x86)\王岩\Typora\imgs\image-20230324144409607.png)

### 事件捕获
从DOM的根元素开始去执行对应的事件(从外到里)

  ```js
  object.addEventListener(事件类型，事件处理函数，是否使用捕获机制)
  ```

  ```html
  <body> 
  	<script>
          //document是整个页面，father是一个div，son是一个div  son包含在father中
          //点击son对应的盒子，先后弹出：我是爷爷、我是爸爸、我是儿子
          const fa = document.querySelector('.father')
          const son = document.querySelector('.son')
          document.addEventListener('click', function () {alert('我是爷爷')
          }, true)
          fa.addEventListener('click', function () {alert('我是爸爸')
          }, true)
          son.addEventListener('click', function () {alert('我是儿子')
          }, true)
      </script>
  </body>
  ```

> 说明：addEventListener第三个参数传入true代表是捕获阶段触发（很少使用)
>
> ​			若传入false代表冒泡阶段触发，默认就是false

### 事件冒泡
 当一个元素的事件被触发时，同样的事件将会在该元素的所有祖先元素中依次被触发。这一过程被称为事件冒泡。

  ```js
  fa.addEventListener('click', function () {alert('我是爸爸')})//L2(level 2)第二个参数默认是false
  //冒泡和捕获是相反的
  ```

### 阻止冒泡

* 问题：因为默认就有冒泡模式的存在，所以容易导致事件影响到父级元素

* 需求：若想把事件就限制在当前元素内，就需要阻止事件冒泡

* 前提：阻止事件冒泡需要拿到事件对象

* 语法

  ```js
  事件对象.stopPropagation()
  //此方法可以阻断事件流动传播，不光在冒泡阶段有效，捕获阶段也有效
  ```

### 阻止默认行为

```html
<form action="http://www.itcast.cn">
    <input type="submit" value="免费注册">
</form>
<a href="http://www.baidu.com">百度一下</a>

<script>
    const form = document.querySelector('form')
    form.addEventListener('submit', function (e){
        //阻止默认行为提交  阻止表单提交
        //判断逻辑(表单是否填写完整.... 不完整不让提交)
        e.preventDefault()
    })
    const a = document.querySelector('a')
    a.addEventListener('click', function (e){
        e.preventDefault()//阻止页面跳转
    })
</script>

```

###解绑事件

* on事件方式

  ```js
  btn.onclick = function(){}
  btn.onclick = null //解绑
  ```

* addEventListener方式

  ```js
  btn.removeEventListener(事件类型，事件处理函数，[获取捕获或者冒泡阶段])
  //匿名函数无法被解绑
  ```

* 鼠标经过事件的区别

  ```js
  //mouseover和mouseout会有冒泡效果  注意：鼠标经过over之前必须先离开out
  //假如儿子没有监听事件e，而父亲监听了e，那么儿子就算不处理事件e也会将事件e冒泡给父亲
  //mouseenter和mouseleave没有冒泡效果(推荐)
  ```

### 两种注册事件的区别

传统on注册（L0)

>同一个对象,后面注册的事件会覆盖前面注册(同一个事件)
>
>直接使用null覆盖偶就可以实现事件的解绑
>
>都是冒泡阶段执行的

事件监听注册（L2)
>语法: addEventListener(事件类型,事件处理函数,是否使用捕获)
>
>后面注册的事件不会覆盖前面注册的事件(同一个事件)
>
>可以通过第三个参数去确定是在冒泡或者捕获阶段执行
>
>必须使用removeEventListener(事件类型,事件处理函数,获取捕获或者冒泡阶段)
>
>匿名函数无法被解绑

## 2.事件委托

### 事件委托

>事件委托是利用事件流的特征解决一些开发需求的知识技巧
>
>优点：减少注册次数，可以提高程序性能
>
>原理：事件委托其实是利用事件冒泡的特点。==给父元素注册事件，当我们触发子元素的时候，会冒泡到父元素身上,
>从而触发父元素的事件==。多个子元素需要绑定相同的事件，可以直接给他们的共同父亲绑定事件即可。

```html
<ul>
	<li>第1个孩子</li>
    <li>第2个孩子</li>
    <li>第3个孩子</li>
    <p>我不需要变色</p>
</ul>
<script>
//点击每个小li 当前li文字变为红色
//按照事件委托的方式委托给父级，事件写到父级身上
	const ul = document.querySelector('ul')
    ul.addEventListener('click', function (e) { 
        //this.styLe.coLor = 'red' //这里的this是ul而不是li，因为是ul调用的这个方法
        consoLe.Log(e.target)	//e.target 就是我们点击的那个对象  e.target会获取事件发生的位置处最小的标签对象	
        e.target.style.color = 'red' //又出现新的问题  这样写p标签也会变色
        if (e.target.tagName === 'LI') {//e.target获取对象后判断tagName属性是否是你要修改的标签(大写字符串)
            e.target.style.color = 'red'  
        }
    })
</script>
```

## 3.其他事件

### 页面加载事件

>加载外部资源（如图片、外联CSS和JavaScript等）加载完毕时触发的事件
>
>为什么要学?
>	有些时候需要等页面资源全部处理完了做一些事情
>	老代码喜欢把script写在head中，这时候直接找dom元素找不到
>
>事件名： load
>
>监听页面所有资源加载完毕：
>	给window添加load事件
>
>```javascript
>window.addEventLinstener('load', function(){
>    //等待window(页面所有资源 包括html、图片、外部资源等)加载完毕 再执行这个函数
>    //javaScript写在这里
>    //这样<script></script>标签就可以写在任何地方
>})
>//其他标签也可以加
>img.addEventLinstener('load', function(){
>    //等待这个图片加载完毕 再执行这个函数
>})
>```
>
>但是有时候图片、样式等资源还没有加载完成就可以进行点击交互，这是怎么做到的？
>
>当初始的HTML文档被完全加载和解析完成之后，DOMContentLoaded事件被触发，而无需等待样式表、图像等完全加载。
>
>```js
>document.addEventLinstener('DOMContentLoaded', function(){
>    //监听DOM加载完成后执行
>})
>```

### 元素滚动事件

>滚动条在滚动的时候持续触发的事件
>
>为什么要学?
>	很多网页需要检测用户把页面滚动到某个区域后做一些处理，比如固定导航栏，比如返回顶部
>
>事件名: scroll
>
>监听整个页面滚动:
>```js
>window.addEventLinstener('scroll', function(){
>	//执行的操作
>})//给window和document加都可以
>```
>
>监听某个元素内部滚动直接给某个元素加即可
>
>使用场景:
>	我们想要页面滚动一段距离，比如100px，就让某些元素显示隐藏，那我们怎么知道，页面滚动了100像素呢?
>	就可以使用scroll 来检测滚动的距离~~~
>
>获取位置：scrollLeft和scrollTop（属性)
>	获取元素内容往左、往上滚出去看不到的距离
>	这两个值是可读写的
>
>```js
>window.addEventLinstener('scroll', function(){
>	console.log(this.scrollTop)	//数字型 不带单位  可读写
>})
>```

### 页面尺寸事件

>```js
>window.addEventLinstener('resize', function(){
>    //resize 浏览器窗口大小发生变化的时候触发的事件
>    //比如拖拽、最大化等
>})
>```
>
>

## 4.元素尺寸与位置(未学)

>使用场景:
>前面案例滚动多少距离，都是我们自己算的，最好是页面滚动到某个元素，就可以做某些事。
>简单说，就是通过js的方式，得到元素在页面中的位置
>这样我们可以做，页面滚动到这个位置，就可以做某些操作，省去计算了

# 八、DOM节点操作(vue不用)

DOM节点的增删改

# 九、BOM操作浏览器

## 1.Window对象

### BOM(浏览器对象模型)

>* BOM(Browser Object Model）是浏览器对象模型
>
>  ![image-20230324183043829](D:\Program Files (x86)\王岩\Typora\imgs\image-20230324183043829.png)
>
>* window对象是一个全局对象，也可以说是JavaScript中的顶级对象
>
>* 像document、alert()、console.log()这些都是window的属性，基本BOM的属性和方法都是window的。
>
>* 所有通过var定义在全局作用域中的变量、函数都会变成window对象的属性和方法 let、const不是
>
>* window对象下的属性和方法调用的时候可以省略window

### 定时器-延时函数

>JavaScript内置的一个用来让代码延迟执行的函数，叫setTimeout
>```js
>setTimeout(回调函数，等待的毫秒数)
>```
>
>setTimeout仅仅只执行一次，所以可以理解为就是把一段代码延迟执行，平时省略window清除延时函数:
>
>清除延时函数：
>```js
>let timerId = setTimeout(回调函数，等待的毫秒数)
>clearTimeout(timerId)
>```
>
>注意：
>	延时器需要等待,所以后面的代码先执行 	
>	每一次调用延时器都会产生一个新的延时器  返回值为延时器id

### JS执行机制

* 同步和异步

>JavaScript语言的一大特点就是单线程，也就是说，同一个时间只能做一件事。
>这是因为Javascript 这门脚本语言诞生的使命所致——JavaScript是为处理页面中用户的交互，以及操作DOM而诞生的。比如我们对某个DOM元素进行添加和删除操作，不能同时进行。应该先进行添加，之后再删除。
>单线程就意味着，所有任务需要排队，前一个任务结束，才会执行后一个任务。这样所导致的问题是:如果JS执行的时间过长，这样就会造成页面的渲染不连贯，导致页面渲染加载阻塞的感觉。
>
>为了解决这个问题，利用多核CPU的计算能力，HTML5提出web Worker标准允许JavaScript脚本创建多个线程。于是，JS中出现了同步和异步。
>
>同步任务都在主线程上执行，形成一个执行栈。
>异步任务添加到任务队列。

* JS执行机制

  >1.先执行==执行栈==中的同步任务。
  >
  >2.异步任务交给其他线程执行，执行完后放入任务队列中。
  >3.一旦执行栈中的所有同步任务执行完毕，系统就会按次序读取任务队列中的异步任务，于是被读取的异步任务结束等待状态，进入执行栈，开始执行。
  >
  >![image-20230324184758262](D:\Program Files (x86)\王岩\Typora\imgs\image-20230324184758262.png)
  >
  >![image-20230324185207786](D:\Program Files (x86)\王岩\Typora\imgs\image-20230324185207786.png)

### location对象

* 介绍

>location的数据类型是对象。它拆分并保存了URL地址的各个组成部分

* 常用属性和方法

  >href属性：获取完整的URL地址，对其赋值时用于地址的跳转
  >
  >```js
  >location.href = 'http://www.baidu.com' //可以直接改变当前的url
  >```
  >
  >search属性：获取地址中携带的参数，符号？后面部分
  >
  >```js
  >location.search //获取的是字符串
  >//http://www.baidu.com?username=123&pwd=321   location.search=?username=123&pwd=321
  >```
  >
  >hash属性：获取地址中的哈希值，符号#后面部分
  >
  >```js
  >location.hash
  >// https://music.163.com/#/my/   location.hash = '#/my'
  >//后期vue路由的铺垫，经常用于不刷新页面，显示不同页面比如网易云音乐，导航栏不变，只改变主体部分
  >```
  >
  >reload方法：刷新当前页面，传入参数true时表示强制刷新
  >
  >```js
  >location.reload()//刷新
  >location.reload(true)//强制刷新
  >```

###navigator对象

>记录了浏览器自身的相关信息
>
>userAgent属性：检测浏览器的版本和平台
>
>```js
>//检测userAgent（浏览器信息）
>!(function() {
>    const userAgent = navigator.userAgent
>    //验证是否为Android或iPhone
>    const android = userAgent.match(/(Android);?[\s\/]+([\d.]+)?/)
>    const iphone = userAgent.match(/(iPhone\sOS)\s([\d_]+)/)
>    //如果是Android或iPhone，则跳转至移动站点
>    if (android || iphone){
>        location.href = 'http://m.itcast.cn'
>    }
>})();
>```

###histroy对象(不常用)

>history 的数据类型是对象，主要管理历史记录，该对象与浏览器地址栏的操作相对应，如前进、后退、历史记录等
>
>back()：后退
>
>forward()：前进
>
>go(参数)：1前进一个页面。-1后退一个页面。

## 2.本地存储

### 介绍

>数据存储在用户浏览器中
>设置、读取方便、甚至页面刷新不丢失数据
>容量较大，sessionStorage和localStorage约5M左右

### 分类

* localStorage

  >作用：可以将数据永久存储在本地(用户的电脑)，除非手动删除，否则关闭页面也会存在。
  >
  >特征：
  >	可以多窗口（页面）共享（同一浏览器可以共享)
  >	以键值对的形式存储使用
  >
  >语法：
  >
  >```js
  >localStorge.setItem(k, v)//增/改
  >localStorge.getItem(k)//查
  >localStorge.removeItem(k, v)//删
  >```
  >
  >注意：本地存储只能存储字符串

* sessionStorage

  >特征：
  >	生命周期为关闭浏览器窗口
  >	在同一个窗口(页面)下数据可以共享
  >	以键值对的形式存储使用
  >	用法跟localStorage基本相同

### 存取复杂数据类型

>问题：
>	本地存储只能存字符串，不能存复杂对象。
>	如果存了对象，会将对象变成'[object Object]'这样的字符串 也就是说只能存 不能取。
>
>解决：
>	需要将复杂数据类型转换成JSON字符串,再存储到本地
>
>```js
>JSON.stringify(复杂数据类型)//将对象转为json字符串
>localStorge.setItem('obj', JSON.stringify(obj))
>//存进去和取出来的是json字符串  键和值都带双引号
>
>JSON.parse(JSON字符串)//将json字符串转为对象
>```

# 十、正则表达式
