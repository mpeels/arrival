import { useNavigate } from 'react-router';

export const LandingPage = () => {
    const nav = useNavigate();
    return (
        <div>
            <h1>Arrival</h1>
            <div>
                <button onClick={() => nav('/configure')}>Configure</button>
                <button onClick={() => nav('/queue')}>View Queue</button>
            </div>
        </div>
    );
};
