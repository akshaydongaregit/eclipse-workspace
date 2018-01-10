package test;

public class Editor extends javax.swing.JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	javax.swing.JLayeredPane editorDesktop;
	public Editor()
	{
		super("T-IDE");
		this.setSize(900,600);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setLocation(100,50);
		this.setVisible(true);
		editorDesktop=new javax.swing.JDesktopPane();
		this.add(editorDesktop);
	}
	public void showProjctListFrame()
	{
		javax.swing.JInternalFrame projectListFrame=new javax.swing.JInternalFrame("Projects",true,true,true,true);
		projectListFrame.setLocation(0,0);
		projectListFrame.setSize(200,800);
		projectListFrame.setVisible(true);
		editorDesktop.add(projectListFrame);
	}
	
}
