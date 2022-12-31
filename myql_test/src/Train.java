import java.sql.*;

public class Train { // 创建类Train
    static Connection con; // 声明Connection对象
    static Statement sql; // 声明Statement对象
    static ResultSet res; // 声明ResultSet对象

    public Connection getConnection() { // 与数据库连接方法
        String url = "jdbc:mysql://localhost:3306/demo1?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
        String user = "root";
        String password = "LIL22006";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con; // 返回Connection对象
    }

    public static void main(String[] args) { // 主方法
        Train c = new Train(); // 创建本类对象
        con = c.getConnection(); // 获取与数据库的连接
        try { // try语句捕捉异常
            sql = con.createStatement(); // 实例化Statement对象
            res = sql.executeQuery("select * from student where stuName like '殷%'");// 执行SQL语句
            while (res.next()) { // 如果当前记录不是结果集中的最后一条，进入循环体
                String id = res.getString("stuId");
                String name = res.getString("stuName");
                String age = res.getString("stuAge");
                System.out.print("学号：" + id); // 将列值输出
                System.out.print(" 姓名:" + name);
                System.out.print(" 年龄:" + age+"\n");
            }
        } catch (Exception e) { // 处理异常
            e.printStackTrace(); // 输出异常信息
        }
    }
}