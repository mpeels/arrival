import { useEffect } from 'react';
import { useVisitorQueue } from 'src/api/visitors/useVisitorQueue';
import { Header } from 'src/components/header/Header';
import { QueueEntryDisplay } from './QueueEntryDisplay';
import styles from './QueuePage.module.scss';

export const QueuePage = () => {
    const { queue, fetchVisitorQueue } = useVisitorQueue();

    useEffect(() => {
        fetchVisitorQueue();
        poll();
    }, []);

    const poll = () => {
        setTimeout(() => {
            fetchVisitorQueue();
            poll();
        }, 5000);
    };

    return (
        <div className={styles.queuePage}>
            <Header title="Visitor Queue" showHome />

            <div className={styles.entryWrapper}>
                {queue.entries.map((e) => (
                    <QueueEntryDisplay key={`${e.provider}-${e.number}`} entry={e} />
                ))}
            </div>
        </div>
    );
};
