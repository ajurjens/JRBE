/*
 * Copyright (C) 2014 Alexander Jurjens
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
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Alexander Jurjens
 */
@ManagedBean
public class ChartView implements Serializable {
 
    private LineChartModel lineModel;
    private Analyzer analyzer;
     
    @PostConstruct
    public void init() {
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        try {
            analyzer = new Analyzer(context);
            analyzer.readData();
            ArrayList<Region> regions = analyzer.getRegions();
            Region r = regions.get(0);
            analyzer.calculateHubbertCurve(r.getName());
            
        } catch (Exception ex) {
            Logger.getLogger(ChartView.class.getName()).log(Level.SEVERE, null, ex);
            showFatalError("An error has occured. Please contact Alexander ASAP.");
        }
        createLineModels();
    }
 
    public LineChartModel getLineModel1() {
        return lineModel;
    }
     
    private void createLineModels() {
        lineModel = initLinearModel();
        lineModel.setTitle("Oil Production");
        lineModel.setLegendPosition("e");
        Axis xAxis = lineModel.getAxis(AxisType.X);
        xAxis.setMin(1900);
        xAxis.setMax(2050);
        xAxis.setTickInterval("10");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
    }
     
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
        
        LineChartSeries readProductionSeries = new LineChartSeries();
        
        HubbertCurve[] hcs = analyzer.getHubbertCurves();
        HubbertCurve hc = hcs[0];
        ArrayList<Region> regions = analyzer.getRegions();
        Region r = regions.get(0);
        ArrayList<Integer> readTimeline = r.getReadTimeline();
        ArrayList<Double> readProduction = r.getReadProd();
        
        for (int i = 0; i < readTimeline.size(); i++) {
            readProductionSeries.set(readTimeline.get(i), readProduction.get(i));
        }
        
        readProductionSeries.setLabel("World (BP)");
        
        readProductionSeries.setShowMarker(false);
        
        model.addSeries(readProductionSeries);
        
        ArrayList<Double> HCTimeline = hc.getTimeline();
        ArrayList<Double> HCProduction = hc.getProduction();
        
        LineChartSeries HubbertSeries = new LineChartSeries();
        
        HubbertSeries.setShowMarker(false);
        
        for (int i = 0; i < HCTimeline.size(); i++) {
            HubbertSeries.set(HCTimeline.get(i), HCProduction.get(i));
        }
        
        HubbertSeries.setLabel("World (Hubbert)");
        
        model.addSeries(HubbertSeries);
         
        return model;
    }
    
    public void showFatalError(String error) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal Error!", error + "\nPlease contact Alexander ASAP!");
         
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
}
