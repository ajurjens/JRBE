/*
 * Copyright (C) 2015 Alexander Jurjens
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

import java.util.ArrayList;

/**
 *
 * @author Alexander Jurjens
 */
public class HubbertCurve {
    
       private ArrayList<Double> annualProduction = new ArrayList<>(); // annual Production
       private ArrayList<Double> timeline = new ArrayList<>(); // timeline
       private double peakProduction;
       private double peakYear;
       private ArrayList<Double> multiAnnualProduction = new ArrayList<>(); // Multi-Cycle Hubbert Curves
       private ArrayList<Double> multiTimeline = new ArrayList<>();
    
       public HubbertCurve() {};
       public HubbertCurve(ArrayList<Double> anProd, ArrayList<Double> timeline) {
           annualProduction = anProd;
           this.timeline = timeline;
           
           peakProduction = 0;
            peakYear = 0;
            int peak = 0;
            if (anProd.size() > 0) {
                for (int i = 0; i < (int)anProd.size(); i++) {
                    if (peakProduction < anProd.get(i)) {
                        peakProduction = anProd.get(i);
                        peak = i;
                    }
                }
                peakYear = timeline.get(peak);
            }
       }
       
       public void setProduction(ArrayList<Double> anProd) {
           annualProduction = anProd;
           
           for (int i = 0; i < (int) annualProduction.size(); i++) {
                if (peakProduction < annualProduction.get(i)) {
                    peakProduction = annualProduction.get(i);
                    //peak = i;
                }
            }
       }
       
       public void setTimeline(ArrayList<Double> timeline) { this.timeline = timeline; }
       
       public ArrayList<Double> getProduction() { return annualProduction; }
       
       public ArrayList<Double> getTimeline() { return timeline; }
       
       public double getPeakProduction() { return peakProduction; }
        
       public double getPeakYear() { return peakYear; }
        
       public void setMultiProd(ArrayList<Double> anProd) {
           multiAnnualProduction = anProd;
           
           int peak = 0;
            peakProduction = 0.0;

            for (int i = 0; i < (int) multiAnnualProduction.size(); i++) {
                if (peakProduction < multiAnnualProduction.get(i)) {
                    peakProduction = multiAnnualProduction.get(i);
                    peak = i;
                }
            }
            peakYear = multiTimeline.get(peak);
       }
       public ArrayList<Double> getMultiProd() { return multiAnnualProduction; }
       
       public void setMultiTimeline(ArrayList<Double> timeline) { multiTimeline = timeline; }
       
       public ArrayList<Double> getMultiTimeline() { return multiTimeline; }
}
