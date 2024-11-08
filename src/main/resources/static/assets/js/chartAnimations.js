// 첫 번째 그래프: 장기 실종 아동 현황
function renderMissingChildStatusChart() {
    const ctx = document.getElementById('missingChildStatusChart').getContext('2d');
    new Chart(ctx, {
        type: 'doughnut',
        data: {
            /*출처 아동권리보장원*/
            labels: ['1년이상 장기실종', '20년이상 장기실종'],
            datasets: [{
                data: [56.13, 43.87], 
                backgroundColor: ['#333333', '#A0AEC0'], 
                hoverBackgroundColor: ['#4A4A4A', '#B0BEC5']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: false, // 범례 숨김
                },
                tooltip: {
                    enabled: true,
                    backgroundColor: '#333333', // 말풍선 배경색
                    titleFont: { size: 16, weight: 'bold' }, // 제목 폰트 설정
                    bodyFont: { size: 14 }, // 본문 폰트 설정
                    bodyColor: '#ffffff', // 텍스트 색상
                    padding: 10,
                    displayColors: false, // 색상 사각형 제거
                    cornerRadius: 5, // 말풍선 모서리 둥글게
                    callbacks: {
                        label: function(tooltipItem) {
                            // 말풍선에 표시할 텍스트
                            const label = tooltipItem.label || '';
                            const value = tooltipItem.raw;
                            return `${label}: ${value}%`;
                        }
                    }
                }
            }
        }
    });
}

// 두 번째 그래프: 장기 실종 아동 발견 현황
function renderMissingChildFoundStatusChart() {
    const ctx = document.getElementById('missingChildFoundStatusChart').getContext('2d');
    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['19-23년 실종신고(약 2만6천명)', '미발견(205명)'],
            /*총 실종인원: 263032, 미발견: 205*/
            datasets: [{
                data: [99.92, 0.08], 
                backgroundColor: ['#333333', '#CBD5E0'],
                hoverBackgroundColor: ['#4A4A4A', '#E2E8F0']
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: false, // 범례 숨김
                },
                tooltip: {
                    enabled: true,
                    backgroundColor: '#333333', // 말풍선 배경색
                    titleFont: { size: 16, weight: 'bold' },
                    bodyFont: { size: 14 },
                    bodyColor: '#ffffff',
                    padding: 10,
                    displayColors: false,
                    cornerRadius: 5,
                    callbacks: {
                        label: function(tooltipItem) {
                            const label = tooltipItem.label || '';
                            const value = tooltipItem.raw;
                            return `${label}: ${value}%`;
                        }
                    }
                }
            }
        }
    });
}

// Intersection Observer를 사용하여 그래프 표시
document.addEventListener("DOMContentLoaded", function () {
    const statsSection = document.querySelector('.stats-section');
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                statsSection.style.opacity = '1';
                statsSection.style.transform = 'translateY(0)';
                
                // 그래프 그리기
                renderMissingChildStatusChart();
                renderMissingChildFoundStatusChart();

                observer.unobserve(statsSection); // 한 번 표시 후 관찰 중지
            }
        });
    }, { threshold: 0.1 });
    observer.observe(statsSection);
});

/* 우리아이 찾아보기 */

document.addEventListener("DOMContentLoaded", function () {
    const findChildSection = document.querySelector('.find-child-section');

    // Intersection Observer 설정
    const observer = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                findChildSection.classList.add('fade-in'); // fade-in 클래스 추가
                observer.unobserve(findChildSection); // 한 번 실행 후 관찰 중지
            }
        });
    }, { threshold: 0.1 });

    observer.observe(findChildSection);
});
