export class EventEmitter {
    subscribers = {};

    on(event, cb) {
        if (!this.subscribers[event]) {
            this.subscribers[event] = [];
        }

        this.subscribers[event].push(cb)
    }

    emit(event, ...args) {
        if (!this.subscribers[event]) {
            return;
        }

        this.subscribers[event].forEach(cb => cb(...args));
    }
}

export const issueBus = new EventEmitter();
