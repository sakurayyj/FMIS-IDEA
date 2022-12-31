import java.sql.*; //导入java.sql包

public class Conn { // 创建类Conn
    Connection con; // 声明Connection对象
    //String url = "jdbc:mysql://localhost:3306/demo1?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
    String url = "jdbc:mysql://localhost:3306/data1?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
    String user = "root";
    String password = "LIL22006";


    public Connection getConnection() { // 建立返回值为Connection的方法
        try { // 加载数据库驱动类
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try { // 通过访问数据库的URL获取数据库连接对象
            con = DriverManager.getConnection(url, user,password);
            System.out.println(con+"数据库连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con; // 按方法要求返回一个Connection对象
    }

    public static void main(String[] args) { // 主方法
        Conn c = new Conn(); // 创建本类对象
        c.getConnection(); // 调用连接数据库的方法
    }
}