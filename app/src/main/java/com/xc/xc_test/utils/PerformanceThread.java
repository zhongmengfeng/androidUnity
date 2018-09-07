//package com.xc.xc_test.utils;
//
//import android.app.Application;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by yidouco.ltdyellow on 2018/8/27.
// */
//
//public class PerformanceThread extends Thread {
//    public int mark = Common.getInstance().getMark();
//    public static boolean key = true;
//    public static String device_id;
//    public static String package_name;
//    public static String test_name;
//    // 创建实例
//    private static PerformanceThread performanceTest;
//
//    @Override
//    public void run() {
//        while (key) {// 循环执行该方法
//            try {
//                sleep(15 * 1000);// 线程休眠
//            } catch (InterruptedException e) {
//                e.printStackTrace();// 打印异常
//            }
//            getCpuAndMemDate();// 执行手机数据的方法
//        }
//    }
//
//    public static PerformanceThread getInstance() {
//        if (performanceTest == null) {
//            performanceTest = new PerformanceThread();
//        }
//        return performanceTest;
//    }
//
//    public PerformanceThread() {
//        getDevicesId();
//        PerformanceThread.package_name = Common.PAGEKAGE;
//        output("欢迎使用手机性能监控线程！");
//    }
//
//    public PerformanceThread(String package_name) {
//        getDevicesId();
//        PerformanceThread.package_name = package_name;
//        output("欢迎使用手机性能监控线程！");
//    }
//
//    public PerformanceThread(String testname, String package_name) {
//        getDevicesId();
//        PerformanceThread.package_name = package_name;
//        PerformanceThread.test_name = testname;
//        output("欢迎使用手机性能监控线程！");
//    }
//
//    /**
//     * 结束线程方法
//     */
//    public void stopRecord() {
////        AppLocalMySql.getInstance().ClearUpPerformaceData(mark);
//        PerformanceThread.key = false;
//    }
//    /**
//     * 获取devicesID
//     */
//    public void getDevicesId() {
//        String cmd = "adb devices";
//        String OSname = System.getProperty("os.name");
//        try {
//            Process p = null;
//            if (OSname.contains("Mac")) {
//                p = Runtime.getRuntime().exec(Common.ADB_PATH + cmd);
//            } else {
//                p = Runtime.getRuntime().exec("cmd /c " + cmd);
//            }
//            // 正确输出流
//            InputStream input = p.getInputStream();// 创建并实例化输入字节流
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));// 先通过inputstreamreader进行流转化，在实例化bufferedreader，接收内容
//            String line = "";
//            while ((line = reader.readLine()) != null) {// 循环读取
//                if (line.contains("device")) {
//                    PerformanceThread.device_id = line.replaceAll("device", "");
//                }
//                // System.out.println(line);// 输出
//            }
//            reader.close();// 此处reader依赖于input，应先关闭
//            input.close();
//        } catch (IOException e) {
//            output("执行" + cmd + "失败！");
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取cpu和mem数据，此方法用的是top命令
//     */
//    public void getCpuAndMemDate() {
//        int pss = getMemResult();// 获取内存数据
//        int[] result = getPerformanceResult();// 获取数据
//        int cpu_result = result[0];// 获取cpu
//        int vss = result[1];// 获取虚拟内存数据
//        int rss = result[2];// 获取物理内存数据
//        output("CPU:" + cpu_result, "VSS:" + vss, "RSS:" + rss, "PSS:" + pss);
////        AppLocalMySql.getInstance().saveMemAndCpuResult(mark, package_name, device_id, cpu_result, vss, rss, pss,
////                test_name);// 写入数据库
//    }
//
//    /**
//     * 获取统计结果 利用Linux系统top命令 -m表示个数，-n表示获取次数此处写为1
//     *
//     * @return 返回int数组，包含rss，vss，cpu百分比
//     */
//    public int[] getPerformanceResult() {
//        String cmd = Common.ADB_PATH + "adb shell top -m 8 -n 1";
//        int cpu_result = 0, vss = 0, rss = 0;
//        try {
//            Process p = Runtime.getRuntime().exec(cmd);// 通过runtime类执行cmd命令
//            // 正确输出流
//            InputStream input = p.getInputStream();// 创建并实例化输入字节流
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));// 先通过inputstreamreader进行流转化，在实例化bufferedreader，接收内容
//            String line = "";
//            while ((line = reader.readLine()) != null) {// 循环读取
//                if (line.contains(package_name)) {// 获取行
//                    cpu_result = getCpuDate(line);
//                    vss = getVss(line);
//                    rss = getRss(line);
//                }
//            }
//            reader.close();// 此处reader依赖于input，应先关闭
//            input.close();// 关闭流
//            // 错误输出流
//            InputStream errorInput = p.getErrorStream();// 创建并实例化输入字节流
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorInput));// 先通过inputstreamreader进行流转化，在实例化bufferedreader，接收内容
//            String eline = "";
//            while ((eline = errorReader.readLine()) != null) {// 循环读取
//                System.out.println(eline);// 输出
//            }
//            errorReader.close();// 此处有依赖关系，先关闭errorReader
//            errorInput.close();// 关闭流
//        } catch (IOException e) {
//            output("执行" + cmd + "失败！");
//            e.printStackTrace();
//        }
//        int[] result = { cpu_result, vss, rss };// 新建数组保存数据
//        return result;// 返回数组
//    }
//
//    /**
//     * 获取应用信息 利用Android系统dumpsys命令获取 命令能统计到java虚拟的堆内存和栈内存的使用情况
//     *
//     * @return 返回内存占用
//     */
//    public int getMemResult() {
//        String cmd1 = Common.ADB_PATH + "adb shell dumpsys meminfo " + package_name;
//        int mem_result = 0;
//        try {
//            Process p = Runtime.getRuntime().exec(cmd1);// 通过runtime类执行cmd命令
//            // 正确输出流
//            InputStream input = p.getInputStream();// 创建并实例化输入字节流
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));// 先通过inputstreamreader进行流转化，在实例化bufferedreader，接收内容
//            String line = "";
//            while ((line = reader.readLine()) != null) {// 循环读取
//                if (line.startsWith("        TOTAL")) {
//                    mem_result = getMemInfo(line);
//                }
//            }
//            reader.close();// 此处reader依赖于input，应先关闭
//            input.close();
//            // 错误输出流
//            InputStream errorInput = p.getErrorStream();// 创建并实例化输入字节流
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorInput));// 先通过inputstreamreader进行流转化，在实例化bufferedreader，接收内容
//            String eline = "";
//            while ((eline = errorReader.readLine()) != null) {// 循环读取
//                System.out.println(eline);// 输出
//            }
//            errorReader.close();// 此处有依赖关系，先关闭errorReader
//            errorInput.close();
//        } catch (IOException e) {
//            output("执行" + cmd1 + "失败！", e);
//        }
//        return mem_result;
//    }
//
//    /**
//     * 获取内存信息
//     *
//     * @param info
//     *            截取到的APP信息
//     * @return 返回内存蚕蛹
//     */
//    public int getMemInfo(String info) {
//        int result = 0;
//        Pattern r = Pattern.compile(" [0-9]+ ");
//        Matcher m = r.matcher(info);
//        if (m.find()) {
//            // output(m.group());
//            result = changeStringToInt(m.group().trim());
//        }
//        return result;
//    }
//
//    /**
//     * 获取cpu运行信息
//     *
//     * @param info
//     *            截取到的APP信息
//     * @return 返回CPU占用率，采取double数据格式
//     */
//    public double getCpuInfo(String info) {
//        String percent = info.substring(0, info.indexOf("%"));
//        double result = changeStringToDouble(percent.trim());
//        return result;
//    }
//
//    /**
//     * 获取cpu运行信息
//     *
//     * @param info
//     *            截取到的APP信息
//     * @return 返回CPU占用率，采取int数据格式
//     */
//    public int getCpuDate(String info) {
//        Pattern pattern = Pattern.compile(" [0-9]*%");
//        Matcher matcher = pattern.matcher(info);
//        matcher.find();
//        String date = matcher.group().trim();
//        return changeStringToInt(date.substring(0, date.length() - 1));
//    }
//
//    public int getVss(String info) {
//        Pattern pattern = Pattern.compile(" [0-9]+K");
//        Matcher matcher = pattern.matcher(info);
//        matcher.find();
//        String date = matcher.group().trim();
//        return changeStringToInt(date.substring(0, date.length() - 1));
//    }
//
//    public int getRss(String info) {
//        Pattern pattern = Pattern.compile("K +[0-9]+K");
//        Matcher matcher = pattern.matcher(info);
//        matcher.find();
//        String date = matcher.group().substring(1, matcher.group().length() - 1);
//        return changeStringToInt(date.trim());
//    }
//
//    // 把string类型转化为double
//    public Double changeStringToDouble(String text) {
//        // return Integer.parseInt(text);
//        return Double.valueOf(text);
//    }
//
//    // 把string类型转化为int
//    public int changeStringToInt(String text) {
//        // return Integer.parseInt(text);
//        return Integer.valueOf(text);
//    }
//
//    public void output(String text) {// 明显输出
//        System.out.println(text);
//    }
//
//    public void output(Object... object) {
//        if (object.length == 1) {
//            output(object[0].toString());
//            return;
//        }
//        for (int i = 0; i < object.length; i++) {
//            System.out.println("第" + (i + 1) + "个：" + object[i]);
//        }
//    }
//
//}
