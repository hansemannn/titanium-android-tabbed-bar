package ti.tabbedbar;

import android.app.Activity;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

public class TabbedBar extends TiUIView
{
    private MaterialButtonToggleGroup tabbedBar;
    private RelativeLayout layout;
    private int[] viewIds = new int[]{};

    public TabbedBar(Activity context, TiViewProxy proxy) {
        super(proxy);

        this.layout = new RelativeLayout(context);
        this.tabbedBar = new MaterialButtonToggleGroup(context);
        this.tabbedBar.setSelectionRequired(true);
        this.tabbedBar.setSingleSelection(true);

        this.tabbedBar.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            KrollDict event = new KrollDict();
            int index = -1;

            for (int i = 0; i < viewIds.length; i++) {
                int viewId = viewIds[i];

                if (viewId == checkedId) {
                    index = i;
                }
            }

            event.put("index", index);

            this.proxy.fireEvent("click", event);
        });

        this.layout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

        this.tabbedBar.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

        this.layout.addView(this.tabbedBar);
        setNativeView(this.layout);
    }

    @Override
    public void processProperties(KrollDict properties)
    {
        super.processProperties(properties);

        String[] orderedProperties = {
            "labels",
            "index"
        };

        for (String name : orderedProperties) {
            Object propertyValue = properties.get(name);

            if (propertyValue != null) {
                this.processProperty(name, properties);
            }
        }
    }

    @Override
    public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy) {
        super.propertyChanged(key, oldValue, newValue, proxy);
        this.processProperty(key, proxy.getProperties());
    }

    public void processProperty(String withName, KrollDict fromProperties) {
        if (withName.equals("labels")) {
            setLabels((Object[]) fromProperties.get("labels"));
        } else if (withName.equals("index")) {
            setIndex(fromProperties.getInt("index"));
        }
    }

    @Kroll.setProperty
    public void setIndex(int index) {
        for (int i = 0; i < viewIds.length; i++) {
            int viewId = viewIds[i];

            if (i == index) {
                tabbedBar.check(viewId);
            }
        }
    }

    @Kroll.setProperty
    public void setLabels(Object[] labels) {
        viewIds = new int[labels.length];
        Object selectedIndex = this.proxy.getProperty("index");

        for (int index = 0; index < labels.length; index++) {
            String labelText = (String) labels[index];

            MaterialButton button = new MaterialButton(TiApplication.getAppCurrentActivity(), null, R.attr.materialButtonOutlinedStyle);
            button.setId(ViewCompat.generateViewId());
            button.setAllCaps(false);
            button.setText(labelText);

            viewIds[index] = button.getId();
            tabbedBar.addView(button);
        }
    }
}
