package usr.krina.salescontrol;

import usr.krina.salescontrol.entity.Contractor;
import usr.krina.salescontrol.entity.Product;

import java.util.Optional;

public class EditState {
    private EDIT_MODE mode = EDIT_MODE.NONE;
    private Object data;

    public EditState(EDIT_MODE mode) {
        this.mode = mode;
        data = null;
    }

    public EditState(EDIT_MODE mode, Object data) {
        this.mode = mode;
        this.data = data;
    }

    public Optional<Product> getProduct() {
        if (data instanceof Product) {
            return Optional.of((Product) data);
        }
        return Optional.empty();
    }

    public Optional<Contractor> getContractor() {
        if (data instanceof Contractor) {
            return Optional.of((Contractor) data);
        }
        return Optional.empty();
    }
}
