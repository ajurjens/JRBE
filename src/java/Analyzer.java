
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;
import javax.servlet.ServletContext;

/*
 * Copyright (C) 2014 Alexander
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/**
 *
 * @author Alexander
 */
public class Analyzer {
    
    private final ServletContext context;
    private int cumYear;
    private double cumProd;
    private int first;
    private int last;
    private final ArrayList<Region> regions = new ArrayList<>();
    private int regionPos;
    private int firstYear;
    private int lastYear;
    private ArrayList<Double> readProd;
    private ArrayDeque<Double> cumProds;
    private int begin;
    private int end;
    private ArrayList<Double> readTimeline;
    private boolean calculationFinished;
    private ArrayList<Double> pDivQ;
    private ArrayList<HubbertLine> hls;
    private double slope;
    private double start;
    private double qt;
    private HubbertLine hl;
    private ArrayList<Double> anProds;
    private ArrayList<Double> Q;
    private HubbertCurve hc;
    private ArrayList<HubbertCurve> hcs;
    private ArrayList<Double> timeline;
    
    public Analyzer(ServletContext context) throws IOException {
        this.context = context;
    }
    
    public void readData() throws IOException {
        
        int count;
        ArrayList<Integer> readTimeline = new ArrayList<>();
        int [] multiMaximumPoints = new int [4];
        int [] multiAdditionPoints = new int [4];
        
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(context.getRealPath("/Data")))) {
            for (Path path : dirStream) {
                if (path.toString().endsWith(".txt")) {
                    try (Stream<String> lines = Files.lines(path)) {
                        count = 0;
                        readTimeline.clear();
                        readProd.clear();
                        Iterator<String> it = lines.iterator();
                        while(it.hasNext()) {
                            String line = it.next();
                            if (line.length() > 0) {
                                char c = line.charAt(0);
                                boolean isDigit = (c >= '0' && c <= '9');
                                if (isDigit) {
                                    if (count == 0) {
                                        count++;
                                        String [] data = line.split(" ");
                                        cumYear = Integer.parseInt(data[0]);
                                        cumProd = Double.parseDouble(data[1]);
                                    }
                                    else if (count == 1) {
                                        count++;
                                        String [] data = line.split(" ");
                                        first = Integer.parseInt(data[0]);
                                        last = Integer.parseInt(data[1]);
                                    }
                                    else if (count == 2 || count == 3) {
                                        count++;
                                        String [] data = line.split(" ");
                                        if (count == 2) {
                                            for (int i = 0; i < data.length; i++) {
                                                multiMaximumPoints[i] = Integer.parseInt(data[i]);
                                            }
                                        }
                                        if (count == 3) {
                                            for (int i = 0; i < data.length; i++) {
                                                multiAdditionPoints[i] = Integer.parseInt(data[i]);
                                            }
                                        }
                                    }
                                    else {
                                        String [] data = line.split(" ");
                                        int year = Integer.parseInt(data[0]);
                                        double anPr = Double.parseDouble(data[1]);
                                        readTimeline.add(year);
                                        readProd.add(anPr);
                                    }
                                }
                            }
                        }
                    }
                }
                int slashIndex = path.toString().lastIndexOf("/");
                int dotIndex = path.toString().lastIndexOf(".");
                String name = path.toString().substring(slashIndex + 1, dotIndex);
                Region region = new Region();
                region.setName(name);
                region.setCumYear(cumYear);
                region.setCumProd(cumProd);
                region.setReadProd(readProd);
                region.setReadTimeline(readTimeline);
                region.setFirstYear(first);
                region.setLastYear(last);
                region.setMultiMaximumDataPoints(multiMaximumPoints);
                region.setMultiAdditionDataPoints(multiAdditionPoints);
            }
        }
    }
    
    public void calculateHubbertCurve(String regionName) throws Exception {

        int cumProdTemp;
        ArrayList<Double> oneDivP = new ArrayList<>();
        ArrayDeque<Double> timelinePart1 = new ArrayDeque<>();
        ArrayDeque<Double> timelinePart2 = new ArrayDeque<>();

        for (int i = 0; i < (int)regions.size(); i++) { //global regionPos!
            if (regions.get(i).getName().equals(regionName)) {
                regionPos = i;
            }
        }

        firstYear = regions.get(regionPos).getFirstYear();
        lastYear = regions.get(regionPos).getLastYear();

        //readData(regionName);

        //regions.get(regionPos).setFirstYear(firstYear);
        //regions.get(regionPos).setLastYear(lastYear);

        double temp = cumProd / 0.365;

        for (int i = readProd.size() - 1; i >= 0; i--) {
            cumProds.addFirst(temp);
            temp -= (readProd.get(i));
        }

        begin = -1;
        end = -1;

        for (int i = 0; i < (int)readTimeline.size(); i++) {
            if (firstYear == readTimeline.get(i)) {
                begin = i;
            }
            if (lastYear == readTimeline.get(i)) {
                end = i;
            }
        }

        if ((begin == -1) || (end == -1)) {
            //wxMessageBox(wxT("First datapoint and/or last datapoint choices are illegal. Hubbert Linearization computation will now fail."), wxT("Error"), wxOK | wxICON_ERROR);
            calculationFinished = true;
            throw new Exception("First datapoint and/or last datapoint choices are illegal. Hubbert Linearization computation will now fail.");
        }
        //else if (((end - begin) < 3) && ((end - begin) > 0)) {
            
            //wxMessageBox(wxT("It is not recommended to do Hubbert Linearization with 2 datapoints. Hubbert Linearization gives more unreliable results with less datapoints."), wxT("Error"), wxOK | wxICON_ERROR);
        //}
        else {
            Double[] cPArray = (Double[]) cumProds.toArray();
            for (int i = 0; i < (int)readProd.size(); i++) {
                pDivQ.add((readProd.get(i)) / (cPArray[i]));
            }

            hls.set(regionPos, getHubbertLine());

            int maxQ = (int)qt;

            for (int i = 1; i <= maxQ; i++) {
                anProds.add((start * i * (1 - (((float)i) / qt))));
                oneDivP.add(1 / anProds.get(anProds.size() - 1));
            }
            
            cumProdTemp = cPArray[end].intValue(); // .... - 1 ???

            for (int i = 0; i < (int)cumProds.size(); i++) {
                Q.add(cPArray[i] * 0.365);
            }

            if (cPArray[end] > qt) {
                //wxString region(regionName.c_str(), wxConvUTF8);
                //wxMessageBox(wxT("Error with region: ") + region + wxT("\nCumulative production greater than Ultimate Recoverable Resources.\nPlease set the datapoints for Hubbert Linearization manually."), wxT("Error"), wxOK | wxICON_ERROR);
                ArrayList<Double> nothing1 = new ArrayList<>();
                ArrayList<Double> nothing2 = new ArrayList<>();
                hc = new HubbertCurve(nothing1, nothing2);
                hl = new HubbertLine(0.0f, 0.0f, 0.0f);
                hcs.set(regionPos, hc);
                hls.set(regionPos, hl);
                regions.get(regionPos).setPDivQ(pDivQ);
                regions.get(regionPos).setQ(Q);
            }
            else {
                timelinePart1.addFirst(readTimeline.get(end));
                for (int i = cumProdTemp - 1; i > 0; i--) {
                    timelinePart1.addFirst(timelinePart1.getFirst() - oneDivP.get(i + 1));
                }
                if (cumProdTemp >= 0) {
                    timelinePart2.addFirst(readTimeline.get(end) + oneDivP.get(cumProdTemp));
                }
                for (int i = cumProdTemp + 1; i < maxQ; i++) {
                    timelinePart2.add(timelinePart2.getLast() + oneDivP.get(i));
                }
                
                Double[] tLPart1 = (Double[]) timelinePart1.toArray();
                for (int i = 0; i < (int)timelinePart1.size(); i++) {
                    timeline.add(tLPart1[i]);
                }

                Double[] tLPart2 = (Double[]) timelinePart2.toArray();
                for (int i = 0; i < (int)timelinePart2.size(); i++) {
                    timeline.add(tLPart2[i]);
                }
                regions.get(regionPos).setPDivQ(pDivQ);
                regions.get(regionPos).setQ(Q);
                hc = new HubbertCurve(anProds, timeline);
                hcs.set(regionPos, hc);
               
            }
        }
    }
    
    public HubbertLine getHubbertLine() {

        double sumQxPDivQ = 0;
        double sumPDivQ = 0;
        double sumQ = 0;
        double sumQxQ = 0;

        Double[] cPArray = (Double[]) cumProds.toArray();
        for (int i = begin; i <= end; i++) { // Least Squares Fit
            sumQxPDivQ += (cPArray[i] * pDivQ.get(i));
            sumPDivQ += pDivQ.get(i);
            sumQ += cPArray[i];
            sumQxQ += (cPArray[i] * cPArray[i]);
        }

        slope = (((end - begin + 1) * sumQxPDivQ) - (sumQ * sumPDivQ)) / (((end - begin + 1) * sumQxQ) - (sumQ * sumQ));
        start = (sumPDivQ - (slope * sumQ)) / (end - begin + 1);
        qt = (-1 * (start / slope));
        
        hl = new HubbertLine();

        hl.setSlope(slope);
        hl.setStart(start);
        hl.setQt(qt * 0.365);
        return hl;
    }
}
