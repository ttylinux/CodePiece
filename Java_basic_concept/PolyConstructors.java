package chapter8;

/**
 *Practice 15
 *
 * 在构造器中，调用的方法，其方法修饰符，必须是 private 或者是final。
 * private的话，就保证，构造器中调用的是当前类中的方法；
 * final( 形如protected  final void test()(); public final void test())，
 * 则表示该方法不会被导出类覆盖，所以，构造器中调用的也是当前类中的方法。
 * -----或者，在构造器中，不调用方法。
 *------这样就保证了符合我们所期望的。
 *
 * 下面的输出，表示，
 * 1.先进行父类的实例化
 * 2.调用父类的构造器，而父类的构造器中，调用了draw()方法；
 * 此时，系统就要判定，当前所创建的对象，是导出类的实例还是父类的实例；
 * 判断的结果是，是导出类的实例，所以就调用导出类的draw()方法。
 * 而此时，导出类的draw方法，所访问的导出类的成员还没有被初始化。
 * -----而根据这个机制”在其他任何事物发生之前，将分配给对象的存储空间初始化成二进制的零“；
 * 也就是说，即使导出类此时的各个成员还没有进行初始化，draw方法在输出的没有初始化
 * 的width,height时，值为0。
 * 
 * -------
 * 构造器初始化的过程：
 * 1.从最上层开始，逐层进行初始化
 * 2.每层的初始化顺序是：先初始化各个成员变量；然后调用构造器。
 * 
 *
 */
public class PolyConstructors {
	
	public static void main(String[] args)
	{
		new  RectangularGlyph(15,15);
	}

}

class Glyph {

	protected  final void test()
	{
		
	}
	
	
	protected void draw() {

		System.out.println("Polyph.draw()");
	}

	Glyph() {
		System.out.println("Glyph() before draw()");
		draw();
		System.out.println("Glyph() after draw()");
	}
}

class RectangularGlyph extends Glyph {

	private int width = 1;
	private int height = 1;

	@Override
	protected void draw()
	{
		System.out.println("RectangularGlyph.RectangularGlyph(), width ="
				+ width + ",height =" + height);
	}
	public RectangularGlyph(int mWidth, int mHeight) {
		width = mWidth;
		height = mHeight;

		System.out.println("RectangularGlyph.RectangularGlyph(), width ="
				+ width + ",height =" + height);
	}

}

/**
 * 输出结果 ：
 *Glyph() before draw()
  RectangularGlyph.RectangularGlyph(), width =0,height =0
  Glyph() after draw()
  RectangularGlyph.RectangularGlyph(), width =15,height =15 
 * 
 */
 