package net.hdt.neutronia.base.client.gui.components;

public abstract class GuiComponentResizableComposite extends BaseComposite {
	protected int width;
	protected int height;

	public GuiComponentResizableComposite(int x, int y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}