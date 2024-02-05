package algonquin.cst2335.prabhpreetsandroidlabs.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class MainViewModel extends  ViewModel
{
    public MutableLiveData<String> editString= new MutableLiveData<>();
    public MutableLiveData<Boolean> isSelected= new MutableLiveData<>();

    // Constructor or any initialization method where you set initial value
    public MainViewModel() {

        isSelected.setValue(false); // Set initial value
    }
}