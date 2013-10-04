package pattern;

public class Reuse
{

	public static void main(String[] args)
	{
		Apply.process(new Upcase(), "Upcase_upcase");
		
		Apply.process(new FilterAdapter(new LowPass(1.0)), new Waveform());
	}
	
	//输出结果：
	//Using Processor  Upcase
	//UPCASE_UPCASE
	//Using Processor  LowPass
	//Waveform 0

}





interface Processor
{

	public String name();

	public Object process(Object s);

}
/**
 *这样Apply的代码就可以被任何类复用，只要该类实现了接口Processor。
 *
 *这样，process就和Processor的实现完全解耦了，process方法无需理会
 *Processro是如何实现的，谁都可以实现Processor。
 * 
 */
class Apply{
	
	public static void process(Processor p, Object s)
	{
		System.out.println("Using Processor  " + p.name());
		System.out.println(p.process(s));
	}
}

/**
 * 
 *使用抽象类StringProcessor，因为name()方法的代码在所有子类中都是一样的，
 *所以，在抽象类中实现，然后给所有子类继承(包含去);
 *
 *然后，process方法要定义为抽象的，留给子类去实现，因为每个子类的实现方式
 *都是不同的。 
 *
 *因为父类StringProcessor实现了接口Processor，所以父类是Processor类型，
 *于是，继承父类的所有子类也是Processor类型，所以可以使用Apply。
 */
 abstract class StringProcessor implements Processor{
	
	@Override
	public String name(){
		
		return getClass().getSimpleName();
	}
	@Override
	public abstract String process(Object input);
}
 
 class Upcase extends StringProcessor {

	@Override
	public String process(Object input)
	{
		// TODO Auto-generated method stub
		return ((String)input).toUpperCase();
	}
	 
 }
 
 class Downcase extends StringProcessor{

	@Override
	public String process(Object input)
	{
		// TODO Auto-generated method stub
		return ((String)input).toLowerCase();
	}
	 
 }
 
 /***************************************************************************************/
 
 /**
  * 
  *因为：
  *1.Filter的才是处理Waveform的类
  *2.要使用Apply类，就必须遵守Processor接口中的方法，也就是实现Processor接口
  *-----那么，就可以采取下述的做法：
  *1.对外，是要实现Processor接口，这样FilterAdapter才是Processor类型，从而可以使用Apply类。
  *2.在实现Processor接口时，实际执行处理操作的类是Filter。(用到了代理方式:FilterAdapter不是Filter类型
  *，但是对外却可以访问到filter的方法所提供的功能)。
  *
  *这样FilterAdapter就具备了这样的效果：
  *1.接收你所拥有的接口Filter(访问Filter中的接口)
  *2.然后，生成你所需要的Processor(实现Processor接口)接口对象
  *也就是，FilterAdapter的功能是将Filter接口适配成Processor接口，
  *这样一种设计FilterAdapter类的方式，叫做适配器设计模式。
  *
  * 
  */
 class FilterAdapter implements Processor
 {
    private Filter filter;
    
    public FilterAdapter(Filter filter)
    {
    	this.filter = filter;
    }
	 
	@Override
	public String name()
	{
		// TODO Auto-generated method stub
		return filter.name();
	}

	@Override
	public Waveform process(Object s)
	{
		// TODO Auto-generated method stub
		return filter.process((Waveform)s);
	}
	 
 }
 
 
 class Waveform{
	 
	 private static long counter;
	 private final long id = counter++;
	 @Override
	 public String toString()
	 {
		 return "Waveform " + id;
	 }
 }
 
 /**
  *在处理Wavform时，有自己专门的类Filter来处理 
  */
 class Filter{
	 
	 public String name()
	 {
		 return getClass().getSimpleName();
	 }
	 
	 public Waveform process(Waveform input)
	 {
		 return input;
	 }
 }
 
 class LowPass extends Filter{
	 
	 private double cutoff;
	 public LowPass(double cutoff)
	 {
		 this.cutoff = cutoff;
	 }
	 
	 public Waveform process(Waveform input)
	 {
		 return input;
	 }
 }
 
 
 class HighPass extends Filter{
	 
	 private double cutoff;
	 public HighPass (double cutoff)
	 {
		 this.cutoff = cutoff;
	 }
	 
	 public Waveform process(Waveform input)
	 {
		 return input;
	 }
 }
 
 
 
 
 
 
