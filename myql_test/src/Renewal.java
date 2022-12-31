import java.sql.*;

public class Renewal { // 创建类
    static Connection con; // 声明Connection对象
    static PreparedStatement sql; // 声明PreparedStatement对象
    static ResultSet res; // 声明ResultSet对象

    public Connection getConnection() {
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

    public static void main(String[] args) {
        Renewal c = new Renewal(); // 创建本类对象
        con = c.getConnection(); // 调用连接数据库方法
        try {
            sql = con.prepareStatement("select * from student"); // 查询数据库
            res = sql.executeQuery(); // 执行SQL语句
            System.out.println("执行增加、修改、删除前数据:");
            while (res.next()) {
                String id = res.getString("stuID");
                String name = res.getString("stuName");
                String age = res.getString("stuAge");
                System.out.print("学号：" + id);
                System.out.print(" 姓名：" + name);
                System.out.print(" 年龄:" + age+"\n");
            }
            sql = con.prepareStatement("insert into student(stuId,stuName,stuAge) values(?,?,?)");
            sql.setString(1, "007"); // 预处理添加数据
            sql.setString(2, "sakura");
            sql.setString(3, "22");
            sql.executeUpdate();
            System.out.println("执行增加后的数据:");
            while (res.next()) {
                String id = res.getString("stuId");
                String name = res.getString("stuName");
                String age = res.getString("stuAge");
                System.out.print("学号：" + id); // 将列值输出
                System.out.print(" 姓名:" + name);
                System.out.print(" 年龄:" + age+"\n");
            }
            sql = con.prepareStatement("update student set stuName = ? where stuID = ? ");
            sql.setString(1, "sakura"); // 更新数据
            sql.setString(2, "008"); // 更新数据
            sql.executeUpdate();
            System.out.println("执行修改后的数据:");
            while (res.next()) {
                String id = res.getString("stuId");
                String name = res.getString("stuName");
                String age = res.getString("stuAge");
                System.out.print("学号：" + id); // 将列值输出
                System.out.print(" 姓名:" + name);
                System.out.print(" 年龄:" + age+"\n");
            }
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from student where stuID = 008");
            // 查询修改数据后的tb_stu表中数据
            sql = con.prepareStatement("select * from student");
            res = sql.executeQuery(); // 执行SQL语句
            System.out.println("执行删除后的数据:");
            while (res.next()) {
                String id = res.getString("stuId");
                String name = res.getString("stuName");
                String age = res.getString("stuAge");
                System.out.print("学号：" + id); // 将列值输出
                System.out.print(" 姓名:" + name);
                System.out.print(" 年龄:" + age+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}