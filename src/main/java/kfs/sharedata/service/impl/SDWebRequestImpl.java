package kfs.sharedata.service.impl;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kfs.sharedata.service.SDWebRequest;
import kfs.sharedata.service.SDmobileController;

/**
 *
 * @author pavedrim
 */
public class SDWebRequestImpl implements SDWebRequest {

    public static final String clientSplitStr = "(?=\\d*$)";

    private Long clientId;
    private boolean partnerList = false;
    private boolean clientList = false;
    private boolean clientView = false;
    private boolean notesList = false;
    private boolean noteAdd = false;
    private boolean clientAdd = false;
    private final Gson gs;
    private final SDmobileController ctrl;
    private final String userLogin;
    private final String pathInfo;
    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    SDWebRequestImpl(HttpServletRequest req, HttpServletResponse resp, Gson gs, SDmobileController ctrl) {
        this.req = req;
        this.resp = resp;
        this.gs = gs;
        this.ctrl = ctrl;
        userLogin = req.getUserPrincipal().getName();
        pathInfo = req.getPathInfo();
        if ((pathInfo != null) && (pathInfo.length() > 0)) {
            if (pathInfo.startsWith(ctrl.getPrefixListClients())) {
                clientList = true;
            } else if (pathInfo.startsWith(ctrl.getPrefixListNotes())) {
                clientId = getLongId(pathInfo);
                if (clientId == null) {
                    ctrl.logError("Cannot recognize number from: " + pathInfo, null);
                }
                notesList = clientId != null;
            } else if (pathInfo.startsWith(ctrl.getPrefixNoteAdd())) {
                noteAdd = true;
            } else if (pathInfo.startsWith(ctrl.getPrefixClientAdd())) {
                clientAdd = true;
            } else if (pathInfo.startsWith(ctrl.getPrefixListPartners())) {
                partnerList = true;
            } else if (pathInfo.startsWith(ctrl.getPrefixClientView())) {
                clientId = getLongId(pathInfo);
                if (clientId == null) {
                    ctrl.logError("Cannot recognize number from: " + pathInfo, null);
                }
                clientView = true;
            }
        }
    }

    private static Long getLongId(String pathInfo) {
        String[] pp = pathInfo.split(clientSplitStr, 2);
        if (pp != null) {
            if (pp.length == 2) {
                try {
                    return Long.parseLong(pp[1]);
                } catch (NumberFormatException ex) {
                }
            }
        }
        return null;
    }

    @Override
    public String getUserLogin() {
        return userLogin;
    }

    @Override
    public String getPathInfo() {
        return pathInfo;
    }

    @Override
    public Long getClientId() {
        return clientId;
    }

    @Override
    public boolean isClientList() {
        return clientList;
    }

    @Override
    public boolean isNotesList() {
        return notesList;
    }

    @Override
    public boolean isNoteAdd() {
        return noteAdd;
    }

    @Override
    public <T> T getParameter(String name, Class<T> cls) {
        return gs.fromJson(req.getParameter(name), cls);
    }

    @Override
    public void send(Object obj) {
        resp.setContentType("Content-type: application/json");
        PrintWriter out;
        try {
            out = resp.getWriter();
        } catch (IOException ex) {
            ctrl.logError("Cannot write to the output", ex);
            return;
        }
        String s = gs.toJson(obj);
        out.print(s);
        out.flush();
        out.close();
    }

    @Override
    public void sendError(String message) {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try {
            resp.getWriter().write(message);
            resp.flushBuffer();
        } catch (IOException ex) {
            ctrl.logError("Cannot write to the output", ex);
        }
    }

    @Override
    public boolean isClientAdd() {
        return clientAdd;
    }

    @Override
    public boolean isPartnerList() {
        return partnerList;
    }

    @Override
    public boolean isClientView() {
        return clientView;
    }

}
