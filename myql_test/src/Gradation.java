import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Gradation { // 创建类
    static Connection con; // 声明Connection对象
    static Statement sql; // 声明Statement对象
    static PreparedStatement sql2; // 声明PreparedStatement对象
    static ResultSet res; // 声明ResultSet对象
    String url = "jdbc:mysql://localhost:3306/data1?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
    String user = "root";
    String password = "123456";
    static List<List> data_list = new ArrayList<>();


    public Connection getConnection() { // 建立返回值为Connection的方法
        try { // 加载数据库驱动类
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try { // 通过访问数据库的URL获取数据库连接对象
            con = DriverManager.getConnection(url, user,password);
            //System.out.println("数据库连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con; // 按方法要求返回一个Connection对象
    }

    public void insert(List<List> list){
        Gradation c = new Gradation(); // 创建本类对象
        con = c.getConnection(); // 与数据库建立连接
        try {
            for(int i = 0;i < list.size();i++){
                System.out.println("每一行年份"+list.get(i).get(14));
                sql2 = con.prepareStatement("select * from data1"); // 查询数据库
                // 执行SQL语句，返回结果集
                res = sql2.executeQuery();
                sql2 = con.prepareStatement("insert into data1(x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12,x13,y,year) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                sql2.setInt(1, (Integer) list.get(i).get(0));// 预处理添加数据
                sql2.setDouble(2, (Double) list.get(i).get(1));
                sql2.setDouble(3,(Double) list.get(i).get(2));
                sql2.setDouble(4,(Double) list.get(i).get(3));
                sql2.setDouble(5,(Double) list.get(i).get(4));
                sql2.setInt(6,(Integer) list.get(i).get(5));
                sql2.setDouble(7,(Double) list.get(i).get(6));
                sql2.setDouble(8,(Double) list.get(i).get(7));
                sql2.setDouble(9,(Double) list.get(i).get(8));
                sql2.setDouble(10,(Double) list.get(i).get(9));
                sql2.setDouble(11,(Double) list.get(i).get(10));
                sql2.setDouble(12,(Double) list.get(i).get(11));
                sql2.setInt(13,(Integer) list.get(i).get(12));
                sql2.setDouble(14,(Double) list.get(i).get(13));
                sql2.setInt(15,(Integer) list.get(i).get(14));
                sql2.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void clearMySQL(){
        Gradation c = new Gradation(); // 创建本类对象
        con = c.getConnection(); // 与数据库建立连接
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("delete from data1");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<List> getData(){
        int x1,x6,x13,year;
        Double x2, x3, x4, x5, x7, x8, x9, x10, x11, x12, y;
        Gradation c = new Gradation(); // 创建本类对象
        con = c.getConnection(); // 与数据库建立连接
        try {
            sql = con.createStatement(); // 实例化Statement对象
            // 执行SQL语句，返回结果集
            res = sql.executeQuery("select * from data1");
            while (res.next()) { // 如果当前语句不是最后一条则进入循环
                List list = new ArrayList();
                // 获取数据
                x1 = res.getInt("x1");
                x2 = res.getDouble("x2");
                x3 = res.getDouble("x3");
                x4 = res.getDouble("x4");
                x5 = res.getDouble("x5");
                x6 = res.getInt("x6");
                x7 = res.getDouble("x7");
                x8 = res.getDouble("x8");
                x9 = res.getDouble("x9");
                x10 = res.getDouble("x10");
                x11 = res.getDouble("x11");
                x12 = res.getDouble("x12");
                x13 = res.getInt("x13");
                y = res.getDouble("y");
                year = res.getInt("year");
                list.add(x1);
                list.add(x2);
                list.add(x3);
                list.add(x4);
                list.add(x5);
                list.add(x6);
                list.add(x7);
                list.add(x8);
                list.add(x9);
                list.add(x10);
                list.add(x11);
                list.add(x12);
                list.add(x13);
                list.add(y);
                list.add(year);
                data_list.add(list);
                //System.out.println(x1+", "+x2+", "+x3+", "+x4+", "+x5+", "+x6+", "+x7+", "+x8+", "+x9+", "+x10+", "+x11+", "+x12+", "+x13+", "+y+", "+year); // 将列值输出
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data_list;
    }

    public static void main(String[] args) { // 主方法
        Gradation c = new Gradation();
        data_list = c.getData();
    }
}