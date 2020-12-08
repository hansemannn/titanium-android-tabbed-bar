package ti.tabbedbar;

import android.app.Activity;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

@Kroll.proxy(creatableInModule= TitaniumTabbedBarModule.class, propertyAccessors = {
	"labels",
	"index"
})
public class TabbedBarProxy extends TiViewProxy  {
	@Override
	public TiUIView createView(Activity activity) {
		TiUIView view = new TabbedBar(activity, this);

		view.getLayoutParams().autoFillsHeight = false;
		view.getLayoutParams().autoFillsWidth = false;

		return view;
	}

	protected TabbedBar getView() {
	    return (TabbedBar) this.getOrCreateView();
    }

	// Handle creation options
	@Override
	public void handleCreationDict(KrollDict properties)
    {
		super.handleCreationDict(properties);
	}
}
