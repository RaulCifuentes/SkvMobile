package com.metric.skava.identification.model;

import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by metricboy on 3/29/14.
 */
public class UserDataDomain {

    /*Perhaps add the type of domain READ or WRITE*/

    private HashMap<ExcavationProject, List<Tunnel>> projectTunnels;
    private HashMap<Tunnel, List<TunnelFace>> tunnelFaces;

    public UserDataDomain(List<TunnelFace> faces) {
        projectTunnels = new HashMap<ExcavationProject, List<Tunnel>>();
        tunnelFaces = new HashMap<Tunnel, List<TunnelFace>>();
        for (TunnelFace tunnelFace : faces) {
            addTunnelFace(tunnelFace);
        }
    }

    public List<ExcavationProject> getProjects() {
        ArrayList<ExcavationProject> asList = new ArrayList<ExcavationProject>();
        asList.addAll(projectTunnels.keySet());
        return asList;
    }

    public List<Tunnel> getTunnels(ExcavationProject project) {
        if (projectTunnels.containsKey(project)){
            return projectTunnels.get(project);
        } else {
            return new ArrayList<Tunnel>();
        }

    }

    public List<TunnelFace> getFaces(Tunnel tunnel) {
        if (tunnelFaces.containsKey(tunnel)){
            return tunnelFaces.get(tunnel);
        } else {
            return new ArrayList<TunnelFace>();
        }
    }



    private void addTunnelFace(TunnelFace face) {
        Tunnel tunnel = face.getTunnel();
        if (tunnelFaces.containsKey(tunnel)) {
            tunnelFaces.get(tunnel).add(face);
        } else {
            List<TunnelFace> faces = new ArrayList<TunnelFace>();
            faces.add(face);
            tunnelFaces.put(tunnel, faces);
            addTunnel(tunnel);
        }

    }

    private void addTunnel(Tunnel tunnel) {
        ExcavationProject project = tunnel.getProject();
        if (projectTunnels.containsKey(project)) {
            projectTunnels.get(project).add(tunnel);
        } else {
            List<Tunnel> tunnels = new ArrayList<Tunnel>();
            tunnels.add(tunnel);
            projectTunnels.put(project, tunnels);
        }
    }
}
