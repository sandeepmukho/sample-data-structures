package org.mapreduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Generic Aggregation Job take emit values and reduce values from Configuration
 * from the and aggregates on the same
 * 
 * #Todo - generic reduce incomplete
 * 
 * @author Sandeep Mukhopadhyay
 * 
 */
public class GenericAggregator extends Configured implements Tool, CommonConstants {

    private static final Log LOG = LogFactory.getLog(GenericAggregator.class);

    static HashMap<String, Integer> getColumnConfigMap(String columnMapString) {
        HashMap<String, Integer> columnMap = new HashMap<String, Integer>();
        String[] columnMapLines = columnMapString.split("\n");
        for (int i = 0; i < columnMapLines.length; i++) {
            String lineHere = columnMapLines[i];
            lineHere.replaceAll("\n| ", EMPTY_STRING);
            String[] lineSplits = lineHere.split(TAB);
            if (!lineSplits[0].equalsIgnoreCase(DELIMITER) && !lineSplits[0].equalsIgnoreCase(SEPARATOR))
                columnMap.put(lineSplits[0].toUpperCase(), Integer.parseInt(lineSplits[1]));
        }
        return columnMap;
    }

    static class GenericAggregatorMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

        static String[] mapColumnNames = new String[] {};

        HashMap<String, Integer> columnConfigMap = null;

        static LongWritable one = new LongWritable(1);

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            Configuration confHere = context.getConfiguration();
            String columnMapString = confHere.get("COLUMN_CONFIG_MAP");
            columnConfigMap = getColumnConfigMap(columnMapString);
            mapColumnNames = confHere.get("EMIT").split(COMMA);
        }

        protected void fillOutputKeyText(String[] lineSplits, StringBuilder outKey) {
            if (mapColumnNames.length > 0) {
                for (int i = 0; i < mapColumnNames.length; i++) {
                    int index = columnConfigMap.get(mapColumnNames[i]);
                    outKey.append(lineSplits[index].replace("'", EMPTY_STRING));
                    outKey.append(TAB);
                }
            } else {
                for (int i = 0; i < lineSplits.length; i++) {
                    outKey.append(lineSplits[i].replace("'", EMPTY_STRING));
                    outKey.append(TAB);
                }
            }
        }

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            try {
                String[] lineSplits = line.split(TAB);
                StringBuilder outKey = new StringBuilder();
                /**
                 * fill fields given in columnName from values of line appended
                 * to outValue Key OR out all values
                 */
                fillOutputKeyText(lineSplits, outKey);

                context.write(new Text(outKey.toString()), one);
            } catch (IOException ie) {
                LOG.error("IOEXCEPTION IN 'map' for line:" + line + "\n" + ie.getStackTrace());
                return;
            } catch (InterruptedException ie) {
                LOG.error("INTERRUPTED EXCEPTION IN 'map' for line:" + line + "\n" + ie.getStackTrace());
                return;
            }
        }
    }

    /**
     * Reducer
     * 
     * 
     */
    static class GenericAggregatorReducer extends Reducer<Text, LongWritable, Text, Text> {

        HashMap<String, Integer> columnConfigMap = null;

        static String[] mapColumnNames = new String[] {};

        static String[] reduceColumnNames = new String[] {};

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            Configuration confHere = context.getConfiguration();
            String columnMapString = confHere.get("COLUMN_CONFIG_MAP");
            columnConfigMap = getColumnConfigMap(columnMapString);
            mapColumnNames = confHere.get("EMIT").split(COMMA);
            reduceColumnNames = confHere.get("REDUCE").split(COMMA);
        }

        protected void fillOutputKeyText(String[] lineSplits, StringBuilder outKey) {
            // TODO
            // Calculate the reduce indexes relative to output map values and
            // group the keys
        }

        /**
         * Does simple aggregation of values as of now
         */
        public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException,
                InterruptedException {
            String line = key.toString();
            Long count = 0L;
            try {
                while (values.iterator().hasNext()) {
                    try {
                        count += values.iterator().next().get();
                    } catch (NumberFormatException nfe) {
                        LOG.error("NumberFormatException IN 'reduce' for line:" + line + "\n" + nfe.getStackTrace());
                    }
                }
                context.write(key, new Text(count.toString()));
            } catch (IOException ie) {
                LOG.error("IOEXCEPTION IN 'reduce' for line:" + line + "\n" + ie.getStackTrace());
                return;
            } catch (InterruptedException ie) {
                LOG.error("INTERRUPTED EXCEPTION IN 'reduce' for line:" + line + "\n" + ie.getStackTrace());
                return;
            }
        }

    }

    static String getCommaSeparatedColumnList(String columnIndexMap) throws IOException {
        String commaSeparatedSegmentList = null;
        File file = new File(columnIndexMap);

        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);

        String line = bufIn.readLine();
        line = line.replaceAll(" |\t|\n", EMPTY_STRING);
        commaSeparatedSegmentList = line;

        while ((line = bufIn.readLine()) != null) {
            line = line.replaceAll(" |\t|\n", EMPTY_STRING);
            commaSeparatedSegmentList = commaSeparatedSegmentList + "," + line;
        }
        bufIn.close();
        return commaSeparatedSegmentList;
    }

    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        // String otherArgs[] = new GenericOptionsParser(conf,
        // args).getRemainingArgs();
        List<String> otherArgs = new ArrayList<String>();
        if (args.length < 3) {
            System.out
                    .println("Give 2 input arguments: Input files path, Final outout path, column config map, file containing list of valid pubIds -r <no of reducers>] ");
            System.exit(0);
        }
        int numberOfReducers = 0;
        int j = 0;
        for (int i = 0; i < args.length; ++i) {
            try {
                if ("-r".equals(args[i])) {
                    numberOfReducers = Integer.parseInt(args[++i]);
                } else {
                    otherArgs.add(j, args[i]);
                    j++;
                }
            } catch (NumberFormatException except) {
                System.out.println("ERROR: Integer expected instead of " + args[i]);
                System.out
                        .println("Give 3 input arguments:\n 1. File containing list of input files(new line separated),\n 2. Output path,\n 3. File containing list of config files(new line separated),\n 4. File containing list of segment map files [optional argument -r <no of reducers>]\n");
                return -1;
            } catch (ArrayIndexOutOfBoundsException except) {
                System.out.println("ERROR: Required parameter missing from " + args[i - 1]);
                System.out
                        .println("Give 3 input arguments:\n 1. File containing list of input files(new line separated),\n 2. Output path,\n 3. File containing list of config files(new line separated),\n 4. File containing list of segment map files \n 5.Config file containing universal segment map.[optional argument -r <no of reducers>]\n");
                return -1;
            }
        }
        // #TODO
        // read the EMIT and REDUCE values from command prompt and pass to
        // mapper and reducer

        String inputFileListFilePath = otherArgs.get(0);
        String commaSeparatedInputFileList = getCommaSeparatedColumnList(inputFileListFilePath);
        conf.addResource(new Path(otherArgs.get(2)));
        // conf.set("COLUMN_ID_INDEX", otherArgs.get(3));
        Job job = new Job(conf);

        if (numberOfReducers != 0)
            job.setNumReduceTasks(numberOfReducers);
        job.setJarByClass(GenericAggregator.class);
        job.setMapperClass(GenericAggregatorMapper.class);
        job.setReducerClass(GenericAggregatorReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        if (numberOfReducers != 0) {
            job.setNumReduceTasks(numberOfReducers);
        }
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPaths(job, commaSeparatedInputFileList);
        FileOutputFormat.setOutputPath(job, new Path(otherArgs.get(1)));

        job.waitForCompletion(true);
        System.out.println("Job execution complete.");

        return 0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new GenericAggregator(), args);
        if (res == -1) {
            System.out.println("Error in execution. Exiting!");
        }
    }
}
