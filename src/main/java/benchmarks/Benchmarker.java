package benchmarks;

import java.util.ArrayList;

public class Benchmarker {
    private ArrayList<Benchmark> benchMarks;


    class Benchmark {
        private long start;
        private long stop;
        String description;

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getStop() {
            return stop;
        }

        public void setStop(long stop) {
            this.stop = stop;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
