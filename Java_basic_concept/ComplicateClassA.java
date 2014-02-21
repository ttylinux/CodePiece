import java.util.ArrayList;

/**
 * @author LuShuWei
 */
public class ComplicateClassA implements Cloneable {

	private ArrayList<String> list = new ArrayList<String>();
	private ArrayList<Item> itemList = new ArrayList<Item>();

	public ComplicateClassA() {

	}

	public void addItem(String name) {
		list.add(name);
	}

	public void addInstance(Item item) {
		itemList.add(item);
	}

	public ArrayList<String> getList() {
		return list;
	}
	
	public ArrayList<Item> getInstanceList()
	{
		return itemList;
	}

	// @Override
	// protected Object clone() throws CloneNotSupportedException {
	//
	// ComplicateClassA one = (ComplicateClassA) super.clone();
	// one.list = this.list;
	// return one;
	// }

	@Override
	protected Object clone() throws CloneNotSupportedException {

		ComplicateClassA one = (ComplicateClassA) super.clone();
		one.list = new ArrayList<String>();
		one.itemList = new ArrayList<Item>();
		for (String str : this.list) {
			one.list.add(str);
		}

		for (Item item : this.itemList) {
			one.itemList.add(item);
		}
		return one;
	}

	public static void main(String[] args) {

		ComplicateClassA one = new ComplicateClassA();
		one.addItem("one1");
		one.addItem("one2");
		one.addInstance(new Item("itemInOne1"));
		one.addInstance(new Item("itemInTwo2"));
		try {
			ComplicateClassA two = (ComplicateClassA) one.clone();

			if (two != null && !two.equals(one)) {
				System.out.println("one 和two是两个不同的对象；深度克隆成功");
				two.addItem("two1");
				two.addInstance(new Item("itemInTwo1"));
				System.out.println("list in one:" + one.getList().toString());
				System.out.println("list in two:" + two.getList().toString());
				System.out.println("-------------------------------------");
				System.out.println("itemList in one:" + one.getInstanceList().toString());
				System.out.println("itemList in two:"+two.getInstanceList().toString());

			} else {
				System.out.println("one 和 two是两个相同的对象。深度克隆失败");
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
