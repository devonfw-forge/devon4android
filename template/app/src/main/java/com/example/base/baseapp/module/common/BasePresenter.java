package com.example.base.baseapp.module.common;

import java.util.LinkedList;
import java.util.Queue;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<UI> {
    private Queue<BasePresenter.UICommand<UI>> commandQueue = new LinkedList();
    private UI ui;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BasePresenter() {
    }

    protected void addDisposable(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }

    public void attach(UI ui) {
        this.ui = ui;

        for (BasePresenter.UICommand command = this.commandQueue.poll(); command != null; command = this.commandQueue.poll()) {
            command.execute(ui);
        }
    }

    public void detach() {
        this.compositeDisposable.clear();
        this.ui = null;
    }

    protected void send(BasePresenter.UICommand<UI> command) {
        if (this.ui != null) {
            command.execute(this.ui);
        }
    }

    protected void sendSticky(BasePresenter.UICommand<UI> command) {
        if (this.ui == null) {
            this.commandQueue.add(command);
        } else {
            command.execute(this.ui);
        }
    }

    protected interface UICommand<UI> {
        void execute(UI ui);
    }
}