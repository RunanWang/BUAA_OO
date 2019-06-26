# 第一次规格程序作业std+demo

**（然而并不完全是std，而是根据std加了一点点bug的版本2333）**

如题：

标准调用方法：

```java
import com.oocourse.specs1.AppRunner;

public class Main {
    public static void main(String[] args) throws Exception {
        AppRunner runner = AppRunner.newInstance(MyPath.class, MyPathContainer.class);
        runner.run(args);
    }
}
```

即，传入自己实现的Path类和PathContainer类即可，系统会自动完成实例化。
