assets/common目录为easyweb的源代码，为了方便升级，列出所有改动如下：

login.css中的修改：

```js
//由于路径的原因，第四行图片地址改为：
background-image: url("../images/bg_login2.svg");

//39行  43行改为78
width: 78px;
margin-left: 78px;

//111行高度改为35px
height: 35px;
```

common.js的修改：
```js
这个改动比较大，建议直接覆盖然后用git对比
```

admin.js的修改：
```js
//427行改下地址
content: n ? n : "page/tpl/tpl-theme.html"

//445行改下地址
content: n ? n : "page/tpl/tpl-message.html"

```

index.js的修改：
```js
//第20行开启默认的单页标签
pageTabs: true,
```