public class Features {

    public Map<Integer, Function<Collection<Job>, Integer>> functions;

    public Features() {

        functions = new HashMap<Integer, Function<Collection<Job>, Integer>>();
        functions.put(NumOfTasks.ONETOTWO, j -> j.stream().filter(j.getNumOfTasks() >= 1).filter(j.getNumOfTasks() <= 2).count());
        functions.put(NumOfTasks.THREETOFOUR, j -> j.stream().filter(j.getNumOfTasks() >= 3).filter(j.getNumOfTasks() <= 4).count());
        functions.put(NumOfTasks.ONETOTWO, j -> j.stream().filter(j.getNumOfTasks() >= 5).count());

    }

    public static class NumOfTasks {
        
        public static int ONETOTWO = 0;
        public static int THREETOFOUR = 1;
        public static int FIVEPLUS = 2;

    }

    public static class TotalItems {

        public static int ONETOFOUR = 3;
        public static int FIVETOEIGHT = 4;
        public static int NINETOTWELVE = 5;
        public static int FOURTEENPLUS = 6;

    }

    public static class HighestRewardItem {
        
        public static int ZEROTOFIVE = 7;
        public static int SIXTOTEN = 8;
        public static int ELEVENTOFIFTEEN = 9;
        public static int SIXTEENPLUS = 10;

    }

    public static class HeaviestItem {

        public static int ZEROTOONE = 11;
        public static int TWOTOTHREE = 12;
        public static int FOURPLUS = 13;

    }

    public static class HighestQuantity {

        public static int ONETOTWO = 14;
        public static int THREETOFOUR = 15;
        public static int FIVETOSIX = 16;
        public static int SEVENPLUS = 17;

    }

}
