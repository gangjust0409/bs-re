//localhost store
export default function () {
    setLocalhostStore: list => {
        localStorage.setItem('history', JSON.stringify(list));
    }
    getLocalhostStore: () => {
        return localStorage.getItem('history');
    }
    removeLocalhostStore: () => {
        localStorage.removeItem('history');
    }
}
