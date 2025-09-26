import { Header } from 'src/components/header/Header';
import styles from './QueuePage.module.scss';

export const QueuePage = () => {
    return (
        <div className={styles.queuePage}>
            <Header title="Visitor Queue" showHome />
        </div>
    );
};
