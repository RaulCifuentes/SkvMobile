package com.metric.skava.calculator.data;

import android.content.Context;


public class MappedIndexDataProvider {

    private static MappedIndexDataProvider instance;
    private static Context context;

    public static MappedIndexDataProvider getInstance(Context ctx) {
        if (instance == null) {
            instance = new MappedIndexDataProvider();
            context = ctx;
        }
        return instance;
    }

//    public List<Jn> getAllJn() {
//        ArrayList<Jn> list = new ArrayList<Jn>();
//        list.add(new Jn("A", "Massive, no or few joints.", 1d));
//        list.add(new Jn("B", "One joint set.", 2d));
//        list.add(new Jn("C", "One joint set plus random joints.", 3d));
//        list.add(new Jn("D", "Two joint sets.", 4d));
//        list.add(new Jn("E", "Two joint sets plus random joints.", 6d));
//        list.add(new Jn("F", "Three joint sets.", 9d));
//        list.add(new Jn("G", "Three joint sets plus random joints.", 12d));
//        list.add(new Jn(
//                "H",
//                "Four or more joints sets, random heavily jointed 'sugar cube', etc.",
//                15d));
//        list.add(new Jn("J", "Crushed rock, earth like.", 20d));
//        return list;
//
//    }
//
//    public List<Jr> getAllJr(int type) {
//        ArrayList<Jr> list = new ArrayList<Jr>();
//        switch (type) {
//            case Jr.a:
//                Jr jr = new Jr("A", "Discontinuous joints.", 4d);
//                jr.setGroupType(Jr.a);
//                list.add(jr);
//                jr = new Jr("B", "Rough or irregular, undulating.", 3d);
//                jr.setGroupType(Jr.a);
//                list.add(jr);
//                jr = new Jr("C", "Smooth, undulating.", 2d);
//                jr.setGroupType(Jr.a);
//                list.add(jr);
//                jr = new Jr("D", "Slickensided, undulating.", 1.5d);
//                jr.setGroupType(Jr.a);
//                list.add(jr);
//                jr = new Jr("E", "Rough, irregular, planar.", 1.5d);
//                jr.setGroupType(Jr.a);
//                list.add(jr);
//                jr = new Jr("F", "Smooth, planar.", 1d);
//                jr.setGroupType(Jr.a);
//                list.add(jr);
//                jr = new Jr("G", "Slickensided, planar.", 0.5d);
//                jr.setGroupType(Jr.a);
//                list.add(jr);
//                break;
//
//            case Jr.b:
//                jr = new Jr("A", "Discontinuous joints.", 4d);
//                jr.setGroupType(Jr.b);
//                list.add(jr);
//                jr = new Jr("B", "Rough or irregular, undulating.", 3d);
//                jr.setGroupType(Jr.b);
//                list.add(jr);
//                jr = new Jr("C", "Smooth, undulating.", 2d);
//                jr.setGroupType(Jr.b);
//                list.add(jr);
//                jr = new Jr("D", "Slickensided, undulating.", 1.5d);
//                jr.setGroupType(Jr.b);
//                list.add(jr);
//                jr = new Jr("E", "Rough, irregular, planar.", 1.5d);
//                jr.setGroupType(Jr.b);
//                list.add(jr);
//                jr = new Jr("F", "Smooth, planar.", 1d);
//                jr.setGroupType(Jr.b);
//                list.add(jr);
//                jr = new Jr("G", "Slickensided, planar.", 0.5d);
//                jr.setGroupType(Jr.b);
//                list.add(jr);
//                break;
//
//            case Jr.c:
//                jr = new Jr("H", "Zone containing clay minerals thick enough to prevent rock-wall contact when sheared.", 1d);
//                jr.setGroupType(Jr.c);
//                list.add(jr);
//                break;
//        }
//        return list;
//
//    }
//
//    public List<Ja> getAllJa(int type) {
//        ArrayList<Ja> list = new ArrayList<Ja>();
//        switch (type) {
//            case Ja.a:
//                list.add(new Ja(
//                        "A",
//                        "Tightly healed, hard, non-softening, impermeable filling, i.e quartz or epidote.",
//                        0.75d));
//                list.add(new Ja("B",
//                        "Unaltered joint walls, surface staining only.", 1d));
//                list.add(new Ja(
//                        "C",
//                        "Slightly altered joint walls. Non-softening mineral coating: sandy particles, clay-free disintegrated rock, etc.",
//                        2d));
//                list.add(new Ja(
//                        "D",
//                        "Silty or sandy clay coatings, small clay fraction (non-softening).",
//                        3d));
//                list.add(new Ja(
//                        "E",
//                        "Softening or low friction clay mineral coatings, i.e. kaolinite or mica. Also chlorite, talc gypsum, graphite, etc. and small quantities of swelling clays.",
//                        4d));
//                for (Ja ja : list) {
//                    ja.setGroupType(Ja.a);
//                }
//                break;
//
//            case Ja.b:
//                list.add(new Ja("F",
//                        "Sandy particles, clay-free disintegrated rock, etc", 4d));
//                list.add(new Ja(
//                        "G",
//                        "Strongly over-consoldated, non-softening clay material fillings (continuous, but <5mm thickness). ",
//                        6d));
//                list.add(new Ja(
//                        "H",
//                        "Medium or low over-consolidation, softening, clay material fillings (continuous, but <5mm thickness).",
//                        8d));
//                list.add(new Ja(
//                        "J",
//                        "Swelling-clay filling, i.e., montmorillonite (continuous, but <5mm thickness). Value of Ja depends on percent of swelling clay-size particles.",
//                        10d));
//                for (Ja ja : list) {
//                    ja.setGroupType(Ja.b);
//                }
//                break;
//
//            case Ja.c:
//                list.add(new Ja(
//                        "K",
//                        "Zones or bands of disintegrated or crushed rock. Strongly over-consolidated.",
//                        6d));
//                list.add(new Ja(
//                        "L",
//                        "Zones or bands of clay, disintegrated or crushed rock. Medium or low over-consolidation or softening fillings.",
//                        8d));
//                list.add(new Ja(
//                        "M",
//                        "Zones or bands of clay, disintegrated or crushed rock. Swelling clay. Ja depends on percent of swelling clay-size particles.",
//                        10d));
//                list.add(new Ja(
//                        "N",
//                        "Thick continuous zones or bands of clay. Strongly over-consolidated.",
//                        10d));
//                list.add(new Ja(
//                        "O",
//                        "Thick, continuous zones or bands of clay. Medium to low over-consolidation.",
//                        13d));
//                list.add(new Ja(
//                        "P",
//                        "Thick, continuous zones or bands with clay. Swelling clay. Ja depends on percent of swelling clay-size particles.",
//                        16d));
//                for (Ja ja : list) {
//                    ja.setGroupType(Ja.c);
//                }
//                break;
//
//        }
//
//        return list;
//
//    }
//
//    public List<Jw> getAllJw() {
//        ArrayList<Jw> list = new ArrayList<Jw>();
//        list.add(new Jw(
//                "A",
//                "Dry excavations or minor inflow (humid or a few drips).",
//                1d));
//        list.add(new Jw(
//                "B",
//                "Medium indflow, ocassional outwash of joint filling (many drips/'rain').",
//                0.66d));
//        list.add(new Jw(
//                "C",
//                "Jet inflow or high pressure in competent rock with unfilled joints.",
//                0.5d));
//        list.add(new Jw(
//                "D",
//                "Large inflow or high pressure, considerable outwash of joint fillings.",
//                0.33d));
//        list.add(new Jw(
//                "E",
//                "Exceptionally high inflow or water pressure decaying with time. Causes outwash of material and perhaps cave in",
//                0.15d));
//        list.add(new Jw(
//                "F",
//                "Exceptionally high inflow or water pressure continuing without noticeable decay. Causes outwash of material and perhaps cave in",
//                0.075d));
//        return list;
//    }
//
//    public List<SRF> getAllSrf(int type) {
//        ArrayList<SRF> list = new ArrayList<SRF>();
//        switch (type) {
//            case SRF.a:
//                list.add(new SRF(
//                        "A",
//                        "Multiple occurrences of weak zones within a short section containing clay or chemically disintegrated, very loose surrounding rock (any depth), or long sectinos with incompetent (weak) rock (any depth).",
//                        10d));
//                list.add(new SRF(
//                        "B",
//                        "Multiple shear zones within a short section in competent clay-free rock with loose surrounding rock (any depth).",
//                        7.5d));
//                list.add(new SRF(
//                        "C",
//                        "Single weak zones within a short section in competent clay-free rock with loose surroundin rock (any depth).",
//                        5d));
//                list.add(new SRF(
//                        "D",
//                        "Loose, open joints, heavily jointed or 'sugar cube', etc.",
//                        5d));
//                list.add(new SRF(
//                        "E",
//                        "Single weak zones with or without clay or chemical disintegrated rock (depth > 50m).",
//                        2.5d));
//                for (SRF srf : list) {
//                    srf.setGroupType(SRF.a);
//                }
//                break;
//            case SRF.b:
//                list.add(new SRF(
//                        "F",
//                        "Low stres, near surface, open joints.",
//                        2.5d));
//                list.add(new SRF(
//                        "G",
//                        "Medium stress, favourable stress condition.",
//                        1d));
//                list.add(new SRF("H",
//                        "High stress, very tigh structure. Usually favourable to stability", 2d));
//                list.add(new SRF("J", "Moderate spalling and/or slabbing after > 1 hour in massive rock.",
//                        25d));
//                list.add(new SRF("K",
//                        "Spalling or rock burst after a few minutes in massive rock.", 100d));
//                list.add(new SRF("L", "Heavy rock burst and immediate dynamic deformation in massive rock.",
//                        300d));
//                for (SRF srf : list) {
//                    srf.setGroupType(SRF.b);
//                }
//                break;
//            case SRF.c:
//                list.add(new SRF("M", "Mild squeezing rock pressure.", 7.5d));
//                list.add(new SRF("N", "Heavy squeezing rock pressure.", 15d));
//                for (SRF srf : list) {
//                    srf.setGroupType(SRF.c);
//                }
//                break;
//            case SRF.d:
//                list.add(new SRF("O", "Mild swelling rock pressure.", 7.5d));
//                list.add(new SRF("P", "Heavy swelling rock pressure.", 12.5d));
//                for (SRF srf : list) {
//                    srf.setGroupType(SRF.d);
//                }
//                break;
//        }
//
//        return list;
//    }

//    public List<RQD_RMR> getAllRqdRmr() {
//        ArrayList<RQD_RMR> list = new ArrayList<RQD_RMR>();
//        list.add(new RQD_RMR(
//                "A",
//                "< 25 %",
//                3d));
//        list.add(new RQD_RMR(
//                "B",
//                "25 % - 50 %",
//                8d));
//        list.add(new RQD_RMR(
//                "C",
//                "50 % - 75 %",
//                13d));
//        list.add(new RQD_RMR(
//                "D",
//                "75 % - 90 %",
//                17d));
//        list.add(new RQD_RMR(
//                "E",
//                "90 % - 100 %",
//                20d));
//
//        return list;
//    }
//
//    public List<Spacing> getAllSpacings() {
//        ArrayList<Spacing> list = new ArrayList<Spacing>();
//        list.add(new Spacing(
//                "A",
//                "> 2 m",
//                20d));
//        list.add(new Spacing(
//                "B",
//                "0.6 - 2 m",
//                15d));
//        list.add(new Spacing(
//                "C",
//                "200 - 600 mm",
//                10d));
//        list.add(new Spacing(
//                "D",
//                "60 - 200 mm",
//                8d));
//        list.add(new Spacing(
//                "E",
//                "< 60 mm",
//                5d));
//        return list;
//    }
//
//
//    public List<ConditionDiscontinuities> getAllConditions() {
//        ArrayList<ConditionDiscontinuities> list = new ArrayList<ConditionDiscontinuities>();
//        list.add(new ConditionDiscontinuities(
//                "A",
//                "Very rough surfaces\t" +
//                        "Not continuous\t" +
//                        "No separation\t" +
//                        "Unweathered wall rock",
//                30d));
//        list.add(new ConditionDiscontinuities(
//                "B",
//                "Rough surfaces\t" +
//                        "Separation < 1 mm\t" +
//                        "Slightly weathered walls",
//                25d));
//        list.add(new ConditionDiscontinuities(
//                "C",
//                "Slightly rough surfaces\t" +
//                        "Separation < 1 mm\t" +
//                        "Highly weathered walls",
//                20d));
//        list.add(new ConditionDiscontinuities(
//                "D",
//                "Slickensided surfaces\t" +
//                        "or Gouge < 5 mm thick\t" +
//                        "or Separation 1-5 mm\t" +
//                        "Continuous",
//                10d));
//        list.add(new ConditionDiscontinuities(
//                "E",
//                "Soft gouge >5 mm thick\t" +
//                        "or Separation > 5 mm\t" +
//                        "Continuous",
//                0d));
//
//
//        return list;
//    }
//
//    public List<StrengthOfRock> getAllStrenghts() {
//        ArrayList<StrengthOfRock> list = new ArrayList<StrengthOfRock>();
//
//        StrengthOfRock strengthOfRock = new StrengthOfRock("A", "> 10 MPa", "> 250 MPa", 15d);
//        list.add(strengthOfRock);
//
//        strengthOfRock = new StrengthOfRock("B", "4 - 10 MPa", "100 - 250 MPa", 12d);
//        list.add(strengthOfRock);
//
//        strengthOfRock = new StrengthOfRock("C", "2 - 4 MPa", "50 - 100 MPa", 7d);
//        list.add(strengthOfRock);
//
//        strengthOfRock = new StrengthOfRock("D", "1 - 2 MPa", "25 - 50 MPa", 4d);
//        list.add(strengthOfRock);
//
//        strengthOfRock = new StrengthOfRock("E", "-", "5 - 25 MPa", 2d);
//        list.add(strengthOfRock);
//
//        strengthOfRock = new StrengthOfRock("F", "-", "1 - 5 MPa", 1d);
//        list.add(strengthOfRock);
//
//        strengthOfRock = new StrengthOfRock("G", "-", "< 1 MPa", 0d);
//        list.add(strengthOfRock);
//
//
//        return list;
//    }
//
//    public List<Groundwater> getAllGroundwaters() {
//        ArrayList<Groundwater> list = new ArrayList<Groundwater>();
//        Groundwater groundwater = new Groundwater("A", "None", "0", "Completely Dry", 15d);
//        list.add(groundwater);
//        groundwater = new Groundwater("B", "< 10", "< 0.1", "Damp", 10d);
//        list.add(groundwater);
//        groundwater = new Groundwater("C", "10-25", "0.1 - 0.2", "Wet", 7d);
//        list.add(groundwater);
//        groundwater = new Groundwater("D", "25 - 125", "0.2 - 0.5 ", "Dripping", 4d);
//        list.add(groundwater);
//        groundwater = new Groundwater("E", "> 125", "> 0.5", "Flowing", 0d);
//        list.add(groundwater);
//        return list;
//    }
//
//    public List<Persistence> getAllPersistences() {
//        List<Persistence> list = new ArrayList<Persistence>();
//        Persistence persistence = new Persistence("A", "< 1 m", 6d);
//        list.add(persistence);
//        persistence = new Persistence("B", " 1 - 3 m", 4d);
//        list.add(persistence);
//        persistence = new Persistence("C", "3 - 10 m", 2d);
//        list.add(persistence);
//        persistence = new Persistence("D", "10 - 20 m", 1d);
//        list.add(persistence);
//        persistence = new Persistence("E", "> 20 m", 0d);
//        list.add(persistence);
//        return list;
//    }
//
//    public List<Roughness> getAllRoughness() {
//        List<Roughness> list = new ArrayList<Roughness>();
//        Roughness roughness = new Roughness("A", "Very rough", 6d);
//        list.add(roughness);
//        roughness = new Roughness("B", "Rough", 4d);
//        list.add(roughness);
//        roughness = new Roughness("C", "Slightly rough", 2d);
//        list.add(roughness);
//        roughness = new Roughness("D", "Smooth", 1d);
//        list.add(roughness);
//        roughness = new Roughness("E", "Slickensided", 0d);
//        list.add(roughness);
//        return list;
//    }
//
//    public List<Infilling> getAllInfillings() {
//        List<Infilling> list = new ArrayList<Infilling>();
//        Infilling infilling = new Infilling("A", "None", 6d);
//        list.add(infilling);
//        infilling = new Infilling("B", "Hard filling < 5 mm", 4d);
//        list.add(infilling);
//        infilling = new Infilling("C", "Hard filling > 5 mm", 2d);
//        list.add(infilling);
//        infilling = new Infilling("D", "Soft filling < 5 mm", 1d);
//        list.add(infilling);
//        infilling = new Infilling("E", "Soft filling > 5 mm", 0d);
//        list.add(infilling);
//        return list;
//    }
//
//    public List<Weathering> getAllWeatherings() {
//        List<Weathering> list = new ArrayList<Weathering>();
//        Weathering weathering = new Weathering("A", "Unweathered", 6d);
//        list.add(weathering);
//        weathering = new Weathering("B", "Slightly", 4d);
//        list.add(weathering);
//        weathering = new Weathering("C", "Moderately", 2d);
//        list.add(weathering);
//        weathering = new Weathering("D", "Highly", 1d);
//        list.add(weathering);
//        weathering = new Weathering("E", "Decomposed", 0d);
//        list.add(weathering);
//        return list;
//    }
//
//
//    public List<Aperture> getAllApertures() {
//        List<Aperture> list = new ArrayList<Aperture>();
//        Aperture aperture = new Aperture("A", "None", 6d);
//        list.add(aperture);
//        aperture = new Aperture("B", "< 0.1 mm", 4d);
//        list.add(aperture);
//        aperture = new Aperture("C", "0.1 - 1 mm", 2d);
//        list.add(aperture);
//        aperture = new Aperture("D", "1 - 5 mm", 1d);
//        list.add(aperture);
//        aperture = new Aperture("E", "> 5 mm", 0d);
//        list.add(aperture);
//        return list;
//    }
//
//    public List<OrientationDiscontinuities> getAllOrientationDiscontinuities(int type) {
//        List<OrientationDiscontinuities> list = new ArrayList<OrientationDiscontinuities>();
//        switch (type) {
//            case OrientationDiscontinuities.TUNNEL_MINES:
//                OrientationDiscontinuities orientation = new OrientationDiscontinuities("A", "Very favourable", 0d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("B", "Favourable", -2d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("C", "Fair", -5d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("D", "Unfavourable", -10d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("E", "Very unfavourable", -12d);
//                list.add(orientation);
//                for (OrientationDiscontinuities ori : list) {
//                    ori.setGroupType(OrientationDiscontinuities.TUNNEL_MINES);
//                }
//                break;
//            case OrientationDiscontinuities.FOUNDATIONS:
//                orientation = new OrientationDiscontinuities("A", "Very favourable", 0d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("B", "Favourable", -2d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("C", "Fair", -7d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("D", "Unfavourable", -15d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("E", "Very unfavourable", -25d);
//                list.add(orientation);
//                for (OrientationDiscontinuities ori : list) {
//                    ori.setGroupType(OrientationDiscontinuities.FOUNDATIONS);
//                }
//                break;
//            case OrientationDiscontinuities.SLOPES:
//                orientation = new OrientationDiscontinuities("A", "Very favourable", 0d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("B", "Favourable", -5d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("C", "Fair", -25d);
//                list.add(orientation);
//                orientation = new OrientationDiscontinuities("D", "Unfavourable", -50d);
//                list.add(orientation);
//                for (OrientationDiscontinuities ori : list) {
//                    ori.setGroupType(OrientationDiscontinuities.SLOPES);
//                }
//                break;
//        }
//
//        return list;
//    }
//
//    public List<ESR> getAllESR() {
//        List<ESR> list = new ArrayList<ESR>();
//        list.add(new ESR("A", "Temporary mine openings, etc.", 4d));
//        list.add(new ESR("B (i)", "Vertical shafts : circular sections", 2.5d));
//        list.add(new ESR("B (ii)", "Vertical shafts : rectangular/square section", 2d));
//        list.add(new ESR("C", "Permanent mine openings, water tunnels for hydro power (exclude high pressure penstocks) water supply tunnels, pilot tunels, drifts and heading for large openings.", 1.6d));
//        list.add(new ESR("D", "Vertical shafts : rectangular/square section", 1.3d));
//        list.add(new ESR("E", "Power houses, storage rooms, water treatment plants, major road and railway tunnels, civil defence chambers, portals, intersections, etc.", 1d));
//        list.add(new ESR("F", "Underground nuclear power stations, railway stations, sports and public facilities, factories, etc.", 0.8d));
//        list.add(new ESR("G", "Very important caverns and underground openings with a long lifetime, ≈ 100 years or without access for maintenance.", 0.8d));
//        return list;
//
//    }
}
