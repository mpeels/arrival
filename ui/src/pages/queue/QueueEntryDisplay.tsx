import type { QueueEntry } from 'src/api/visitors/model';
import styles from './QueueEntryDisplay.module.scss';

type Props = {
    entry: QueueEntry;
};
export const QueueEntryDisplay = ({ entry }: Props) => {
    return (
        <div className={styles.queueEntryDisplay}>
            <div className={styles.providerName}>{entry.provider}</div>
            <div className={styles.number}> {entry.number ?? '---'}</div>
        </div>
    );
};
