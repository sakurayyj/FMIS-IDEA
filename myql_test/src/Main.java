import sun.security.util.Length;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        new Thread(new startServer()).start();
    }

    private static int port = 6666; //端口号
    public static Vector<UserThread> socketList = new Vector<UserThread>();  //消息队列

    private static class startServer extends Thread{
        public void run(){
            try {
                //绑定服务器要监听的端口
                ServerSocket serverSocket = new ServerSocket(port);
                while(true){
                    //socketList.clear();
                    //从队列中取出连接请求，使得队列能及时腾出空位，以容纳新的连接请求
                    Socket socket = serverSocket.accept();
                    System.out.println(""+socket);
                    UserThread userThread = new UserThread(socket);
                    socketList.add(userThread);
                    new Thread(userThread).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class UserThread implements Runnable {
        private Socket skt;
        private DataInputStream dis;
        private DataOutputStream dos;

        public DataOutputStream getDos() {
            return dos;
        }

        public UserThread(Socket socket) {
            skt = socket;
        }

        //接收send过来的线程
        @Override
        public void run() {
            Gradation gradation = new Gradation();
            int x1,x6,x13,year = 0;
            Double x2, x3, x4, x5, x7, x8, x9, x10, x11, x12, y = 0.00;
            int data_len;
            try {
                dos = new DataOutputStream(skt.getOutputStream());
                dis = new DataInputStream(skt.getInputStream());
                while (true) {
                    String title; //获取到的年份或者是标题
                    List<List> data_list = new ArrayList<>();
                    title = dis.readUTF();
                    System.out.println("title:"+title);
                    if (title.equals("获取")) {
                        //发送出去
                        data_len = gradation.getData().size(); //数据库中数据行数
                        System.out.println("title = " + title);
                        System.out.println("数据库中数据行数 = " + data_len);
                        for (UserThread ut : socketList) {
                            System.out.println(socketList.size());
                            try {
                                for(int i = 0;i < data_len;i++){
                                    //writeUTF在写入数据流的时候会加上两个字节以表示字节的长度
                                    ut.getDos().writeInt((Integer) gradation.getData().get(i).get(0));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(1));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(2));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(3));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(4));
                                    ut.getDos().writeInt((Integer) gradation.getData().get(i).get(5));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(6));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(7));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(8));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(9));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(10));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(11));
                                    ut.getDos().writeInt((Integer) gradation.getData().get(i).get(12));
                                    ut.getDos().writeDouble((Double) gradation.getData().get(i).get(13));
                                    ut.getDos().writeInt((Integer) gradation.getData().get(i).get(14));
                                    System.out.println("写出去" + gradation.getData().get(i).get(0));
                                }
                                //刷新
                                ut.getDos().flush();
                                //socketList.clear();
                                //socketList.remove(0);
                                break;
                            } catch (Exception e) {
                                socketList.remove(ut);
                                e.printStackTrace();
                                break;
                            }
                        }
                    }else if(title.equals("写入")){
                        List<List> mysqlList = new ArrayList<>();
                        gradation.clearMySQL();
                        int len = dis.readInt();
                        System.out.println("获取到的数据行数："+len);
                        if(len == 0){
                            break;
                        }
                        for(int i = 0;i < len;i++){
                            x1 = dis.readInt();
                            x2 = dis.readDouble();
                            x3 = dis.readDouble();
                            x4 = dis.readDouble();
                            x5 = dis.readDouble();
                            x6 = dis.readInt();
                            x7 = dis.readDouble();
                            x8 = dis.readDouble();
                            x9 = dis.readDouble();
                            x10 = dis.readDouble();
                            x11 = dis.readDouble();
                            x12 = dis.readDouble();
                            x13 = dis.readInt();
                            y = dis.readDouble();
                            year = dis.readInt();
                            List list = new ArrayList();
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
                            mysqlList.add(list);
                        }
                        System.out.println("写入前行数"+mysqlList.size());
                        gradation.insert(mysqlList);
//                        if(socketList.size()!=0){
//                            socketList.remove(0);
//                        }
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
