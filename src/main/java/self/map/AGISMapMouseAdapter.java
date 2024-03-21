package self.map;

import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;

import java.awt.event.*;


public abstract class AGISMapMouseAdapter implements MouseListener, MouseMotionListener, MouseWheelListener {
    protected final MouseListener mouseListener;
    protected final MouseMotionListener mouseMotionListener;
    protected final MouseWheelListener mouseWheelListener;

    public AGISMapMouseAdapter(GISMap map) {
        this.mouseListener = new PanMouseInputListener(map);
        this.mouseMotionListener = (MouseMotionListener) this.mouseListener;
        this.mouseWheelListener = new ZoomMouseWheelListenerCursor(map);
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
