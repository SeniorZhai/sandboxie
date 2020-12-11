# Sandboxie
将既定规则的xml转换成View的工具

## 容器
用于排列子项
- row 横向布局
- column 纵向布局

## 显示View
- text
    + text [String] 需要显示的文本
    + color [String] 字体颜色 例如`#FFFFFF`
    + size [Int] 字体大小
```xml
<text
    text="测试语句"
    color="#00FF00"
    size="12"
    />
```
- image
    + url [String] 属性 需要显示的图片
```xml
<image
    width="32"
    height="32"
    url="https://avatars0.githubusercontent.com/u/5416585?s=400&amp;u=5407f225bb7b3e0ed4e6777da339896ebf6d0b6"
    />
```


### 通用属性
- flex 占据容器的比重
- width 限制宽度
- height 限制高度
- padding 内边距
- margin 外边距
    + left
    + right
    + top
    + bottom

### 容器属性
- gravity
- round 设置圆角

## TODO
- action
