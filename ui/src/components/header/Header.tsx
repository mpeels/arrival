import { HomeLink } from '../../components/home/HomeLink';
import styles from './Header.module.scss';

type Props = {
    title: string;
    showHome?: boolean;
};
export const Header = ({ title, showHome }: Props) => {
    return (
        <div className={styles.header}>
            <div>
                {showHome && <HomeLink />}
                <h1>{title}</h1>
            </div>
        </div>
    );
};
