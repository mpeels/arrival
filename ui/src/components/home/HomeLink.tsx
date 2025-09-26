import { useNavigate } from 'react-router';
import HomeImage from '../../assets/home.svg';
import { PAGES } from '../../pages/AppRoutes';
import styles from './HomeLink.module.scss';

export const HomeLink = () => {
    const nav = useNavigate();

    return (
        <button className={styles.homeLink} onClick={() => nav(PAGES.HOME)}>
            <img alt="Link to home" src={HomeImage} />
        </button>
    );
};
