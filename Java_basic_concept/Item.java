public class Item implements Cloneable {

	private String name = "ItemInstance";

	public Item(String name) {

		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString()
	{
		return name;
	}

	@Override
	protected Item clone() throws CloneNotSupportedException {
		return (Item) super.clone();
	}

}
