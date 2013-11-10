package chapter10;

public class FirstOuter {

	public class InnerFirst {

		private String name;

		{

			System.out.println("InnerFirst");
		}

		public InnerFirst(String name) {
			this.name = name;
		}
	}

}
