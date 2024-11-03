<!-- JavaScript for sidebar toggle -->
    // 사이드바 토글 기능
    document.getElementById('toggleSidebar').addEventListener('click', function() {
    const sidebar = document.getElementById('sidebar');
    const toggleBtn = document.getElementById('toggleSidebar');
    const mainContent = document.getElementById('main-content');

    if (sidebar.style.left === '0px') {
    sidebar.style.left = '-250px';
    toggleBtn.classList.remove('toggle-active');
    mainContent.style.marginLeft = '150px'; // 콘텐츠를 중앙으로 이동
} else {
    sidebar.style.left = '0px';
    toggleBtn.classList.add('toggle-active');
    mainContent.style.marginLeft = '250px';
}
});


// 로그아웃 시 세션에서 userEmail, selectedChildId 제거
document.addEventListener('DOMContentLoaded', function() {
    // Get the logout link element
    const logoutLink = document.querySelector('a[href="/logout"]');

    // Add a click event listener to the logout link
    if (logoutLink) {
        logoutLink.addEventListener('click', function() {
            // Remove only 'adminEmail' from sessionStorage
            sessionStorage.removeItem('userEmail');
            sessionStorage.removeItem('selectedChildId');
            console.log("adminEmail removed from session storage on logout.");
        });
    }
});