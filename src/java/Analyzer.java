
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    
    public Analyzer(ServletContext context) throws IOException {
        this.context = context;
    }
    
    public void readData() throws IOException {
        
        int count;
        ArrayList<Integer> readTimeline = new ArrayList<>();
        ArrayList<Double> readProd = new ArrayList<>();
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
}
