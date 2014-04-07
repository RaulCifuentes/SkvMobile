package com.metric.skava.app.data;

import android.content.Context;

import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;
import com.metric.skava.discontinuities.model.DiscontinuityShape;
import com.metric.skava.discontinuities.model.DiscontinuityType;
import com.metric.skava.discontinuities.model.DiscontinuityWater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 2/21/14.
 */
public class SkavaDataProvider {

    private static SkavaDataProvider instance;
    private static Context context;


    public static SkavaDataProvider getInstance(Context ctx) {
        if (instance == null) {
            instance = new SkavaDataProvider();
            context = ctx;
        }
        return instance;
    }

    public List<ExcavationProject> getAllProjects() {
        List<ExcavationProject> list;
        list = new ArrayList<ExcavationProject>();
        list.add(new ExcavationProject("A", "ExcavationProject A "));
        list.add(new ExcavationProject("B", "ExcavationProject B "));
        list.add(new ExcavationProject("C", "ExcavationProject C "));
        list.add(new ExcavationProject("D", "ExcavationProject D "));
        list.add(new ExcavationProject("Z", "Choose project ..."));

        return list;
    }


    public List<Tunnel> getAllTunnels() {
        List<Tunnel> list;
        list = new ArrayList<Tunnel>();
        list.add(new Tunnel("A", "Tunnel A "));
        list.add(new Tunnel("B", "Tunnel B "));
        list.add(new Tunnel("C", "Tunnel C "));
        list.add(new Tunnel("D", "Tunnel D "));
        list.add(new Tunnel("Z", "Choose tunnel ..."));
        return list;
    }

    public List<TunnelFace> getAllFaces() {
        List<TunnelFace> list;
        list = new ArrayList<TunnelFace>();
        list.add(new TunnelFace("A", "TunnelFace A "));
        list.add(new TunnelFace("B", "TunnelFace B "));
        list.add(new TunnelFace("C", "TunnelFace C "));
        list.add(new TunnelFace("D", "TunnelFace D "));
        list.add(new TunnelFace("Z", "Choose face ..."));
        return list;
    }

    public List<ExcavationSection> getAllSections() {
        List<ExcavationSection> list;
        list = new ArrayList<ExcavationSection>();
        list.add(new ExcavationSection("A", "Complete "));
        list.add(new ExcavationSection("B", "Bottom "));
        list.add(new ExcavationSection("C", "Right Side "));
        list.add(new ExcavationSection("D", "Left Side "));
        list.add(new ExcavationSection("E", "Roof "));
        list.add(new ExcavationSection("F", "Center "));
        list.add(new ExcavationSection("Z", "Choose section ..."));
        return list;
    }


    public List<ExcavationMethod> getAllMethods() {
        List<ExcavationMethod> list;
        list = new ArrayList<ExcavationMethod>();
        list.add(new ExcavationMethod("A", "Drill & Blast "));
        list.add(new ExcavationMethod("B", "TBM "));
        list.add(new ExcavationMethod("C", "Excavation "));
        list.add(new ExcavationMethod("D", "Partial Blast "));
        list.add(new ExcavationMethod("Z", "Choose method ..."));
        return list;
    }

    public List<DiscontinuityType> getAllDiscontinuitiesTypes() {
        List<DiscontinuityType> list = new ArrayList<DiscontinuityType>();
        list.add(new DiscontinuityType("A", "Fault"));
        list.add(new DiscontinuityType("B", "Joint"));
        list.add(new DiscontinuityType("C", "Stratification"));
        list.add(new DiscontinuityType("D", "Foliation"));
        return list;
    }

    public List<DiscontinuityRelevance> getAllDiscontinuityRelevances() {
        List<DiscontinuityRelevance> list = new ArrayList<DiscontinuityRelevance>();
        list.add(new DiscontinuityRelevance("A", "Primary"));
        list.add(new DiscontinuityRelevance("B", "Random"));
        return list;
    }


    public List<DiscontinuityWater> getAllDiscontinuityWaters() {
        List<DiscontinuityWater> list = new ArrayList<DiscontinuityWater>();
        list.add(new DiscontinuityWater("A", "Dry"));
        list.add(new DiscontinuityWater("B", "Damp"));
        list.add(new DiscontinuityWater("C", "Wet"));
        list.add(new DiscontinuityWater("D", "Dripping"));
        list.add(new DiscontinuityWater("E", "Flowing"));
        return list;
    }


    public List<DiscontinuityShape> getAllDiscontinuityShapes() {
        List<DiscontinuityShape> list = new ArrayList<DiscontinuityShape>();
        list.add(new DiscontinuityShape("A", "Stepped"));
        list.add(new DiscontinuityShape("B", "Undulating"));
        list.add(new DiscontinuityShape("C", "Planar"));
        return list;
    }


//    public List<FractureType> getAllFractureTypes() {
//        List<FractureType> list = new ArrayList<FractureType>();
//        list.add(new FractureType("A", "Massive"));
//        list.add(new FractureType("B", "Blocks"));
//        list.add(new FractureType("C", "Tabular"));
//        list.add(new FractureType("D", "Massive"));
//        list.add(new FractureType("E", "Columnar"));
//        list.add(new FractureType("F", "Irregular"));
//        list.add(new FractureType("G", "Crushed"));
//        return list;
//    }


//    public List<RockMass> getAllRockMassQualities() {
//        List<RockMass> list = new ArrayList<RockMass>();
//        list.add(new RockMass("A (i)", "Exceptionally good"));
//        list.add(new RockMass("A (ii)", "Extremely good"));
//        list.add(new RockMass("A (iii)", "Very good"));
//        list.add(new RockMass("B", "Good"));
//        list.add(new RockMass("C", "Fair"));
//        list.add(new RockMass("D", "Poor"));
//        list.add(new RockMass("E", "Very poor"));
//        list.add(new RockMass("F", "Extremely poor"));
//        list.add(new RockMass("G", "Exceptionally poor"));
//
//    }
}
