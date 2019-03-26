# Java Character 类

Character 类用于对单个字符进行操作。

Character 类在对象中包装一个基本类型 **char** 的值

然而，在实际开发过程中，我们经常会遇到需要使用对象，而不是内置数据类型的情况。为了解决这个问题，Java语言为内置数据类型char提供了包装类Character类。

Character类提供了一系列方法来操纵字符。你可以使用Character的构造方法创建一个Character类对象，例如：

```
Character ch = new Character('a');
```

在某些情况下，Java编译器会自动创建一个Character对象。

例如，将一个char类型的参数传递给需要一个Character类型参数的方法时，那么编译器会自动地将char类型参数转换为Character对象。 这种特征称为装箱，反过来称为拆箱。

## Character 方法

下面是Character类的方法：

| 序号 |                          方法与描述                          |
| :--: | :----------------------------------------------------------: |
|  1   | [isLetter()](http://www.runoob.com/java/character-isletter.html) 是否是一个字母 |
|  2   | [isDigit()](http://www.runoob.com/java/character-isdigit.html) 是否是一个数字字符 |
|  3   | [isWhitespace()](http://www.runoob.com/java/character-iswhitespace.html) 是否是一个空白字符 |
|  4   | [isUpperCase()](http://www.runoob.com/java/character-isuppercase.html) 是否是大写字母 |
|  5   | [isLowerCase()](http://www.runoob.com/java/character-islowercase.html) 是否是小写字母 |
|  6   | [toUpperCase()](http://www.runoob.com/java/character-touppercase.html) 指定字母的大写形式 |
|  7   | [toLowerCase](http://www.runoob.com/java/character-tolowercase.html)() 指定字母的小写形式 |
|  8   | [toString](http://www.runoob.com/java/character-tostring.html)() 返回字符的字符串形式，字符串的长度仅为1 |