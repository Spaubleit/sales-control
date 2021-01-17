package usr.krina.salescontrol;

import usr.krina.salescontrol.entity.Contractor;
import usr.krina.salescontrol.entity.Product;
import usr.krina.salescontrol.entity.Retail;
import usr.krina.salescontrol.entity.Wholesale;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class EditState {
    private EDIT_MODE mode = EDIT_MODE.NONE;
    private Object data = null;
    private Consumer<EditState> callback;

    public EditState(Consumer<EditState> callback) {
        this.callback = callback;
    }

    public void clear() {
        mode = EDIT_MODE.NONE;
        callback.accept(this);
    }

    public void create(Object data) {
        mode = EDIT_MODE.CREATE;
        this.data = data;
        callback.accept(this);
    }

    public void update(Object data) {
        mode = EDIT_MODE.UPDATE;
        this.data = data;
        callback.accept(this);
    }

    public void delete(Object data) {
        mode = EDIT_MODE.DELETE;
        this.data = data;
        callback.accept(this);
    }

    public EDIT_MODE getMode() {
        return mode;
    }

    public Optional<Product> getProduct() {
        if (mode != EDIT_MODE.NONE && data instanceof Product) {
            return Optional.of((Product) data);
        }
        return Optional.empty();
    }

    public Optional<Contractor> getContractor() {
        if (mode != EDIT_MODE.NONE && data instanceof Contractor) {
            return Optional.of((Contractor) data);
        }
        return Optional.empty();
    }

    public Optional<Wholesale> getWholesale() {
        if (mode != EDIT_MODE.NONE && data instanceof Wholesale) {
            return Optional.of((Wholesale) data);
        }
        return Optional.empty();
    }

    public Optional<Retail> getRetail() {
        if (mode != EDIT_MODE.NONE && data instanceof Retail) {
            return Optional.of((Retail) data);
        }
        return Optional.empty();
    }
}
