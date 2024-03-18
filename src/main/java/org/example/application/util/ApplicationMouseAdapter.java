package org.example.application.util;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;

import java.awt.event.*;


public class ApplicationMouseAdapter implements MouseListener, MouseMotionListener, MouseWheelListener {
    private final MouseListener mouseListener;
    private final MouseMotionListener mouseMotionListener;
    private final MouseWheelListener mouseWheelListener;

    public ApplicationMouseAdapter(JXMapViewer viewer) {
        this.mouseListener = new PanMouseInputListener(viewer);
        this.mouseMotionListener = (MouseMotionListener) this.mouseListener;
        this.mouseWheelListener = new ZoomMouseWheelListenerCursor(viewer);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseListener.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseListener.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseListener.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseListener.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseListener.mouseExited(e);
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMotionListener.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseMotionListener.mouseMoved(e);
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelListener.mouseWheelMoved(e);
    }
}
